package com.z1.pokedex.feature.favorites.data.datasource.local

import com.z1.pokedex.core.database.dao.PokemonFavoriteDao
import com.z1.pokedex.feature.favorites.domain.repository.mapper.asFavoriteEntity
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.flow

class PokemonFavoriteLocalDataSourceImpl(
    private val pokemonFavoriteDao: PokemonFavoriteDao
) : PokemonFavoriteLocalDataSource {
    override suspend fun getPokemonFavorites(userId: String) =
        flow {
            pokemonFavoriteDao.getPokemonFavoriteList(userId)
                .collect {
                    emit(it)
                }
        }
}