package com.cvalera.ludex.data.datasource.remote

import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.utils.Endpoints
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.cvalera.ludex.BuildConfig
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IGDBJsonRemoteDataSource @Inject constructor() : GameRemoteDataSource {

    private var currentPage = 1

    companion object {
        private const val CLIENT_ID = BuildConfig.CLIENT_ID
        private const val API_KEY = BuildConfig.AUTHORIZATION_TOKEN
        private const val PAGE_SIZE = 40
    }

    init {
        IGDBWrapper.setCredentials(CLIENT_ID, API_KEY)
    }

    override suspend fun fetchGames(): List<Game> = withContext(Dispatchers.IO) {
        val offset = (currentPage - 1) * PAGE_SIZE
        val apicalypse = APICalypse()
            .fields("*,platforms.platform_logo.*, involved_companies.company.*, cover.*, release_dates.*, external_games.*")
            .limit(PAGE_SIZE)
            .offset(offset)
            .sort("rating_count", Sort.DESCENDING)

        try {
            val json = IGDBWrapper.apiJsonRequest(Endpoints.GAMES, apicalypse.buildQuery())
            parseGamesJson(json)
        } catch (e: RequestException) {
            emptyList()
        }
    }

    override suspend fun searchGames(query: String): List<Game> = withContext(Dispatchers.IO) {
        val offset = (currentPage - 1) * PAGE_SIZE
        val apicalypse = APICalypse()
            .search(query)
            .fields("*,platforms.platform_logo.*, involved_companies.company.*, cover.*, release_dates.*, external_games.*")
            .limit(PAGE_SIZE)
            .offset(offset)

        try {
            val json = IGDBWrapper.apiJsonRequest(Endpoints.GAMES, apicalypse.buildQuery())
            //TODO Ordenar por rating
            parseGamesJson(json) //.sortedByDescending { it.id } DEBERÍA ESTAR ORDEDNADO POR RATING
        } catch (e: RequestException) {
            emptyList()
        }
    }

    private fun parseGamesJson(json: String): List<Game> {
        val gson = Gson()
        val gameType = object : TypeToken<List<IGDBGame>>() {}.type
        val apiGames: List<IGDBGame> = gson.fromJson(json, gameType)

        return apiGames.filter {
            it.id != 0 && it.name.isNotEmpty() && !it.platforms.isNullOrEmpty()
        }.map { game ->
            val devsNames = game.involved_companies?.firstOrNull()?.company?.name
            val releaseDate = game.release_dates?.firstOrNull()?.human
            val platformIconList = game.platforms.mapNotNull { it.platform_logo?.image_id }
            Game(
                id = game.id.toLong(),
                titulo = game.name,
                imagen = imageBuilder(game.cover?.image_id ?: ""),
                plataforma = imageIconBuilder(platformIconList),
                status = GameStatus.SIN_CLASIFICAR,
                fav = false,
                sinopsis = game.summary ?: "",
                dev = devsNames,
                releaseDate = releaseDate
            )
        }
    }

    private fun imageBuilder(imageID: String): String {
        return com.api.igdb.utils.imageBuilder(imageID, ImageSize.COVER_BIG, ImageType.PNG)
    }

    private fun imageIconBuilder(imageIDs: List<String>): List<String> {
        return imageIDs.map { com.api.igdb.utils.imageBuilder(it, ImageSize.COVER_SMALL, ImageType.PNG) }
    }

    override fun nextPage() {
        currentPage++
    }

    override fun resetPagination() {
        currentPage = 1
    }
}

data class IGDBGame(
    val id: Int,
    val name: String,
    val summary: String?,
    val cover: Image?,
    val platforms: List<Platform>,
    val involved_companies: List<InvolvedCompany>?,
    val release_dates: List<ReleaseDate>?
)

data class Image(val image_id: String)
data class Platform(val platform_logo: Image?)
data class InvolvedCompany(val company: Company)
data class Company(val name: String)
data class ReleaseDate(val human: String?)
