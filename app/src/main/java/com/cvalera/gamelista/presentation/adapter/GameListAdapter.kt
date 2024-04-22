package com.cvalera.gamelista.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cvalera.gamelista.domain.model.Game
import com.cvalera.gamelista.domain.model.GameStatus
import com.cvalera.gamelista.R

class GameListAdapter(
    private var gameList: List<Game>,
    private val onClickListener: (Game) -> Unit,
    private val onClickStarListener: (Game) -> Unit,
    private val onAddToListListener: (Game, status: GameStatus) -> Unit
) : RecyclerView.Adapter<GameListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameListViewHolder(
            layoutInflater.inflate(R.layout.item_game_list, parent, false)
        )
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(holder: GameListViewHolder, position: Int) {
        val item = gameList[position]
        holder.render(
            item, onClickListener, onClickStarListener, onAddToListListener, position
        )
    }

    fun updateGames(newGameList: List<Game>) {
        val diffCallback = GameDiffCallback(gameList, newGameList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        gameList = newGameList
        diffResult.dispatchUpdatesTo(this)
    }

}