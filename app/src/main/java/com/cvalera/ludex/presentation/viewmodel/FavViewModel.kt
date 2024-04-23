package com.cvalera.ludex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.domain.usecase.AddFavoriteGameUseCase
import com.cvalera.ludex.domain.usecase.GetFavoriteGamesUseCase
import com.cvalera.ludex.domain.usecase.RemoveFavoriteGameUseCase
import com.cvalera.ludex.domain.usecase.UpdateGameStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val getFavoriteGamesUseCase: GetFavoriteGamesUseCase,
    private val addFavoriteGameUseCase: AddFavoriteGameUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase,
    private val updateGameStatusUseCase: UpdateGameStatusUseCase
) : ViewModel() {
    private val _favGameList = MutableLiveData<List<Game>>(emptyList())
    val favGameList: LiveData<List<Game>> = _favGameList

    fun getListGames() {
        viewModelScope.launch {
            _favGameList.value = getFavoriteGamesUseCase()
        }
    }
    fun searchInList(userFilter: String) {
        viewModelScope.launch {
            val gameFiltered =
                getFavoriteGamesUseCase().filter { game ->
                    game.titulo.lowercase().contains(userFilter.lowercase())
                }
            _favGameList.value = gameFiltered
        }
    }
    fun filterByStatus(status: GameStatus) {
        viewModelScope.launch {
            val gameFiltered =
                getFavoriteGamesUseCase().filter { game ->
                    game.status == status
                }
            _favGameList.value = gameFiltered
        }
    }
    fun onFavItem(game: Game) {
        viewModelScope.launch {
            if (game.fav) {
                removeFavoriteGameUseCase(game)

            } else {
                addFavoriteGameUseCase(game)
            }
            getListGames()
        }
    }
    fun updateGameStatus(game: Game, newStatus: GameStatus) {
        viewModelScope.launch {
            updateGameStatusUseCase(game, newStatus)
            getListGames()
        }
    }
}
