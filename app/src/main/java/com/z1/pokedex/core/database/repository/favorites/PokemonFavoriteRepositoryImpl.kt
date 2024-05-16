package com.z1.pokedex.core.database.repository.favorites

import com.z1.pokedex.core.database.dao.PokemonDao
import com.z1.pokedex.core.database.model.PokemonFavoriteEntity
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonFavoriteRepositoryImpl(
    private val pokemonDao: PokemonDao
): PokemonFavoriteRepository {
    override suspend fun getPokemonFavoritesName(userId: String): Flow<List<String>> =
        flow {
            pokemonDao.getPokemonFavoriteNameList(userId).collect {
                emit(it)
            }
        }

    override suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonDao.insertPokemonFavorite(
            PokemonFavoriteEntity(
                name = pokemon.name,
                url = pokemon.url,
                userId = userId
            )
        )

    override suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonDao.deletePokemonFavorite(
            PokemonFavoriteEntity(
                name = pokemon.name,
                url = pokemon.url,
                userId = userId
            )
        )
}