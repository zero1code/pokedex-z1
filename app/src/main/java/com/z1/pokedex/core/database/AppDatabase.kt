package com.z1.pokedex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.z1.pokedex.core.database.dao.PokemonDao
import com.z1.pokedex.core.database.model.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    autoMigrations = []
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}