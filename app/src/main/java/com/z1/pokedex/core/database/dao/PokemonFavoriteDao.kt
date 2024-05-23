package com.z1.pokedex.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.z1.pokedex.core.database.model.PokemonFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonFavoriteDao {
    @Query("SELECT * FROM tb_favorite WHERE user_id == :userId")
    fun getPokemonFavoriteList(userId: String): Flow<List<PokemonFavoriteEntity>>
    @Query("SELECT name FROM tb_favorite WHERE user_id <= :userId")
    fun getPokemonFavoriteNameList(userId: String): Flow<List<String>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonFavorite(pokemonFavorite: PokemonFavoriteEntity): Long
    @Delete
    suspend fun deletePokemonFavorite(pokemonFavorite: PokemonFavoriteEntity): Int
}