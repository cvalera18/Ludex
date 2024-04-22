package com.cvalera.gamelista.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.model.GameStatus
import com.cvalera.gamelista.domain.usecase.AddFavoriteGameUseCase
import com.cvalera.gamelista.domain.usecase.GetGamesUseCase
import com.cvalera.gamelista.domain.usecase.GetGamesUseCaseFlow
import com.cvalera.gamelista.domain.usecase.LoadMoreGamesUseCase
import com.cvalera.gamelista.domain.usecase.RemoveFavoriteGameUseCase
import com.cvalera.gamelista.domain.usecase.SearchGamesUseCase
import com.cvalera.gamelista.domain.usecase.UpdateGameStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase,
    private val addFavoriteGameUseCase: AddFavoriteGameUseCase,
    private val searchGamesUseCase: SearchGamesUseCase,
    private val removeFavoriteGameUseCase: RemoveFavoriteGameUseCase,
    private val updateGameStatusUseCase: UpdateGameStatusUseCase,
    private val loadMoreGamesUseCase: LoadMoreGamesUseCase,
    getAllGamesUseCase: GetGamesUseCaseFlow,
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    val allGames = getAllGamesUseCase.invoke()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    private val searchQueryStateFlow = MutableStateFlow("")

    init {
        handleSearch()
    }

    private fun handleSearch() {
        searchQueryStateFlow
            .debounce(500) // 500ms de debounce
            .onEach { query ->
                if (query.isNotEmpty()) {
                    searchGames(query)
                }
            }
            .launchIn(viewModelScope)
    }

    fun getListGames() {
        viewModelScope.launch {
            val query = searchQueryStateFlow.value
            _isLoading.value = true
            if (query.isEmpty()) {
                getGamesUseCase()
            } else {
                searchGamesUseCase(query)
            }
            _isLoading.value = false
        }
    }

    private fun searchGames(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                searchGamesUseCase(query)
            }
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
    }

    fun updateGameStatus(game: Game, newStatus: GameStatus) {
        viewModelScope.launch {
            updateGameStatusUseCase(game, newStatus)
        }
    }

    fun configFilter(userFilter: String) {
        searchQueryStateFlow.value = userFilter
    }

    fun loadMoreGames() {
        viewModelScope.launch {
            _isLoading.value = true
            loadMoreGamesUseCase.invoke()
            _isLoading.value = false
        }
    }
}

