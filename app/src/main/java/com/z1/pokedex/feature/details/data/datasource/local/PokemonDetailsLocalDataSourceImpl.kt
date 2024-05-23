package com.z1.pokedex.feature.details.data.datasource.local

import com.z1.pokedex.core.database.dao.PokemonFavoriteDao
import kotlinx.coroutines.flow.flow

class PokemonDetailsLocalDataSourceImpl(
    private val pokemonFavoriteDao: PokemonFavoriteDao
): PokemonDetailsLocalDataSource {
    override suspend fun getPokemonFavoritesName(userId: String) =
        flow {
            pokemonFavoriteDao.getPokemonFavoriteNameList(userId)
                .collect {
                    emit(it)
                }
        }
}