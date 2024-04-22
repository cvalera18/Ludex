package com.cvalera.gamelista.data.datasource.local.database.converters

import androidx.room.TypeConverter
import com.cvalera.gamelista.domain.model.GameStatus

class Converters {
    @TypeConverter
    fun fromGameStatus(value: GameStatus): String {
        return value.name
    }
    @TypeConverter
    fun toGameStatus(value: String): GameStatus {
        return GameStatus.valueOf(value)
    }
}