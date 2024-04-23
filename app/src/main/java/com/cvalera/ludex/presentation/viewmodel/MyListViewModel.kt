package com.cvalera.ludex.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.domain.usecase.AddFavoriteGameUseCase
import com.cvalera.ludex.domain.usecase.GetListedGamesUseCase
import com.cvalera.ludex.domain.usecase.RemoveFavoriteGameUseCase
import com.cvalera.ludex.domain.usecase.UpdateGameStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(
    private val getListedGamesUseCase: GetListedGamesUseCase,
    private val addFavoriteGameUseCase: AddFavoriteGameUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase,
    private val updateGameStatusUseCase: UpdateGameStatusUseCase
) : ViewModel() {

    private val _listedGameList = MutableLiveData<List<Game>>(emptyList())
    val listedGameList: LiveData<List<Game>> = _listedGameList

    fun getListGames() {
        viewModelScope.launch {
            _listedGameList.value = getListedGamesUseCase(GameStatus.SIN_CLASIFICAR)
        }
    }

    fun onFavItem(game: Game) {
        viewModelScope.launch {
            if (game.fav) {
                removeFavoriteGameUseCase(game)
            } else {
                addFavoriteGameUseCase(game)
            }
        }
        updateGameList(game.copy(fav = !game.fav))
    }

    fun updateGameStatus(game: Game, newStatus: GameStatus) {
        viewModelScope.launch {
            updateGameStatusUseCase(game, newStatus)
            updateGameList(game.copy(status = newStatus))
        }
    }

    fun searchInList(userFilter: String) {
        viewModelScope.launch {
            val gameFiltered =
                getListedGamesUseCase(GameStatus.SIN_CLASIFICAR).filter { game ->
                    game.titulo.lowercase().contains(userFilter.lowercase())
                }
            _listedGameList.value = gameFiltered
        }
    }

    fun filterByStatus(status: GameStatus) {
        viewModelScope.launch {
            val gameFiltered =
                getListedGamesUseCase(GameStatus.SIN_CLASIFICAR).filter { game ->
                    game.status == status
                }
            _listedGameList.value = gameFiltered
        }
    }

    private fun updateGameList(updatedGame: Game) {
        val currentList = _listedGameList.value ?: emptyList()
        _listedGameList.value = currentList.map {
            if (it.id == updatedGame.id) updatedGame else it
        }
    }
}