package com.cvalera.ludex.presentation.adapter

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cvalera.ludex.domain.model.Game
import com.cvalera.ludex.domain.model.GameStatus
import com.cvalera.ludex.R
import com.cvalera.ludex.databinding.ItemGameListBinding
class GameListViewHolder(
    view: View
) : ViewHolder(view) {

    private val binding = ItemGameListBinding.bind(view)
    private var currentPosition: Int = -1
    fun render(
        gameListModel: Game,
        onClickListener: (Game) -> Unit,
        onClickStarListener: (Game) -> Unit,
        onAddToListListener: (Game, status: GameStatus) -> Unit,
        position: Int
    ) {
        currentPosition = position
        binding.tvGame.text = gameListModel.titulo
        binding.tvResume.text = gameListModel.sinopsis
        binding.tvStatus.text = itemView.context.getString(gameListModel.status.stringResId)

        Glide.with(binding.ivGame.context).load(gameListModel.imagen).optionalFitCenter()
            .optionalCenterCrop().into(binding.ivGame)
        itemView.setOnClickListener { onClickListener(gameListModel) }
        binding.statusCard.setOnClickListener { showPopup(gameListModel, onAddToListListener) }

        updateIcon(gameListModel)

        //Listener de la estrella
        binding.favCardView.setOnClickListener {
            onClickStarListener.invoke(gameListModel)
        }

        // Ajustar dinámicamente el número de líneas de tvResume
        binding.tvGame.viewTreeObserver.addOnGlobalLayoutListener {
            val lines = binding.tvGame.lineCount
            if (lines > 2) {
                binding.tvResume.maxLines = 2 // Ajusta el número máximo de líneas del tvResume
            } else {
                binding.tvResume.maxLines = 4 // Establece el valor original
            }
        }
    }

    private fun updateIcon(gameListModel: Game) {
        if (gameListModel.status != GameStatus.SIN_CLASIFICAR) {
            binding.ivCircle.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.ivCircle.context,
                    R.drawable.check_circle_outline_24
                )
            )
        } else {
            binding.ivCircle.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.ivCircle.context,
                    R.drawable.bx_circle
                )
            )
        }

        if (!gameListModel.fav) {
            binding.ivStar.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.ivGame.context,
                    R.drawable.baseline_star_outline_24
                )
            )
        } else {
            binding.ivStar.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.ivGame.context,
                    R.drawable.baseline_star_24
                )
            )
        }
    }


    private fun showPopup(game: Game, onAddToListListener: (Game, status: GameStatus) -> Unit) {
        val popupMenu = PopupMenu(binding.ivCircle.context, binding.ivCircle)
        popupMenu.menuInflater.inflate(R.menu.status_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.status_playing -> {
                    onAddToListListener.invoke(game, GameStatus.JUGANDO)
                    true
                }

                R.id.status_completed -> {
                    onAddToListListener.invoke(game, GameStatus.COMPLETADO)
                    true
                }

                R.id.status_sinclasificar -> {
//                    game.setStatusGame("Sin Clasificar")
                    onAddToListListener.invoke(game, GameStatus.SIN_CLASIFICAR)
//                    adapter.notifyDataSetChanged()
                    true
                }

                R.id.status_pendiente -> {
                    onAddToListListener.invoke(game, GameStatus.PENDIENTE)
                    true
                }

                R.id.status_abandonado -> {
                    onAddToListListener.invoke(game, GameStatus.ABANDONADO)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

}
