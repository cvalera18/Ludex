package com.cvalera.gamelista.domain.model
import com.cvalera.gamelista.R

enum class GameStatus(val stringResId: Int) {
    JUGANDO(R.string.status_playing),
    COMPLETADO(R.string.status_completed),
    PENDIENTE(R.string.status_pending),
    SIN_CLASIFICAR(R.string.status_unclassified),
    ABANDONADO(R.string.status_abandoned),
    PROBADO(R.string.status_tried)
}