package com.cvalera.gamelista.data.datasource.remote

import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.cvalera.gamelista.BuildConfig
import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.model.GameStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IGDBGameRemoteDataSource @Inject constructor() : GameRemoteDataSource {

    private var currentPage = 1

    companion object {
        private const val CLIENT_ID = BuildConfig.CLIENT_ID
        private const val API_KEY = BuildConfig.AUTHORIZATION_TOKEN
        private const val PAGE_SIZE = 40
    }

    init {
        IGDBWrapper.setCredentials(CLIENT_ID, API_KEY)
    }

    override suspend fun fetchGames(): List<Game> = withContext(
        Dispatchers.IO
    ) {
        val offset = (currentPage - 1) * PAGE_SIZE
        val apicalypse = APICalypse()
            .fields("*,platforms.platform_logo.*, involved_companies.company.*, cover.*, release_dates.*, external_games.*")
            .limit(PAGE_SIZE)
            .offset(offset)
            .sort("rating_count", Sort.DESCENDING)

        try {
            val wrapperGames = IGDBWrapper.games(apicalypse)
            createGamesFromWrapper(wrapperGames)
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
//            .sort("rating_count", Sort.DESCENDING)
        try {
            val wrapperGames = IGDBWrapper.games(apicalypse)
            createGamesFromWrapper(wrapperGames.sortedByDescending { it.ratingCount })
        } catch (e: RequestException) {
            emptyList()
        }
    }

    private fun createGamesFromWrapper(apiGames: List<proto.Game>): List<Game> {
        return apiGames
            .filter {
                it.id.toInt() != 0 && it.name.isNotEmpty() && !it.platformsList.isNullOrEmpty()
            }
            .map { game ->
                val devsNames = game.involvedCompaniesList.firstNotNullOfOrNull {
                    it.company.name
                }
                val releaseDate = game.releaseDatesList.firstNotNullOfOrNull {
                    it.human
                }
                val platfomsIconList = game.platformsList.map { platform ->
                    platform.platformLogo.imageId
                }
                Game(
                    id = game.id,
                    titulo = game.name,
                    imagen = imageBuilder(game.cover.imageId),
                    plataforma = imageIconBuilder(platfomsIconList),
                    status = GameStatus.SIN_CLASIFICAR,
                    fav = false,
                    sinopsis = game.summary,
                    dev = devsNames,
                    releaseDate = releaseDate
                )
            }
    }

    private fun imageBuilder(imageID: String): String {
        return imageBuilder(imageID, ImageSize.COVER_BIG, ImageType.PNG)
    }

    private fun imageIconBuilder(imageIDs: List<String>): List<String> {
        return imageIDs.map { imageBuilder(it, ImageSize.COVER_SMALL, ImageType.PNG) }
    }

    override fun nextPage() {
        currentPage++
    }

    override fun resetPagination() {
        currentPage = 1
    }

}