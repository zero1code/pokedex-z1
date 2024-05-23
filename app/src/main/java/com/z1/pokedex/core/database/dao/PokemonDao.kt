package com.z1.pokedex.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.z1.pokedex.core.database.model.PokemonEntity
import com.z1.pokedex.core.database.model.PokemonFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM tb_pokemon WHERE page = :page")
    suspend fun getPokemonList(page: Int): List<PokemonEntity>

    @Query("SELECT * FROM tb_pokemon WHERE page <= :page")
    suspend fun getAllPokemonList(page: Int): List<PokemonEntity>
}