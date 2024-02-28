package com.z1.pokedex.core.network.datasource

import com.z1.pokedex.core.network.PokedexApi
import com.z1.pokedex.core.network.mapper.PokemonPageMapper
import com.z1.pokedex.core.network.model.NetworkResult
import com.z1.pokedex.core.network.util.Constants.PAGE_SIZE
import com.z1.pokedex.feature.home.presentation.model.PokemonPage

class PokemonPageDataSource(
    private val api: PokedexApi,
    private val pokemonPageMapper: PokemonPageMapper
): BaseDataSource() {
    suspend fun  fetchPokemonPage(page: Int): PokemonPage {
        val offset = page * PAGE_SIZE
        val response = safeApiCall { api.fetchPokemonPage(offset, PAGE_SIZE) }

        return when(response) {
            is NetworkResult.Success -> pokemonPageMapper.mapDtoToModel(response.data)
            is NetworkResult.Error -> throw Exception(response.message)
        }
    }
}