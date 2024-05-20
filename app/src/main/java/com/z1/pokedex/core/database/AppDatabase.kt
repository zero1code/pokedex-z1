package com.z1.pokedex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.z1.pokedex.core.database.dao.PokemonDao
import com.z1.pokedex.core.database.model.PokemonEntity
import com.z1.pokedex.core.database.model.PokemonFavoriteEntity

@Database(
    entities = [PokemonEntity::class, PokemonFavoriteEntity::class],
    version = 1,
    autoMigrations = []
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
}