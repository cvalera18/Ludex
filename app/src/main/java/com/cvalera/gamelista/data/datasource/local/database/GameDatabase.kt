package com.cvalera.gamelista.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cvalera.gamelista.data.datasource.local.database.converters.Converters
import com.cvalera.gamelista.data.datasource.local.database.dao.GameDao
import com.cvalera.gamelista.data.datasource.local.database.entities.GameEntity

@Database(entities = [GameEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

}
