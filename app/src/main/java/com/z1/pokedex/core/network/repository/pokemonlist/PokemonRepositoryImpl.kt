package com.z1.pokedex.core.network.repository.pokemonlist

import com.z1.pokedex.core.network.PokedexApi
import com.z1.pokedex.core.network.mapper.PokemonPageMapper
import com.z1.pokedex.core.network.model.NetworkResult
import com.z1.pokedex.core.network.util.Constants.PAGE_SIZE
import com.z1.pokedex.feature.home.presentation.model.PokemonPage

class PokemonRepositoryImpl(
    private val api: PokedexApi,
    private val pokemonPageMapper: PokemonPageMapper
) : PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int): NetworkResult<PokemonPage> = runCatching {
        val offset = page * PAGE_SIZE
        val response = api.fetchPokemonPage(offset, PAGE_SIZE)
        response.takeIf { it.isSuccessful }?.body()
            ?.let { NetworkResult.Success(pokemonPageMapper.mapDtoToModel(it)) }
            ?: NetworkResult.Error(response.errorBody().toString())
    }.getOrElse {
        it.printStackTrace()
        NetworkResult.Exception(it)
    }
}