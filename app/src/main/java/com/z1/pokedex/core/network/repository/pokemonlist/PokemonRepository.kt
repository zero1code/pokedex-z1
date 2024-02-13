package com.z1.pokedex.core.network.repository.pokemonlist

import androidx.paging.PagingData
import com.z1.pokedex.core.network.model.PokemonResponse
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun fetchPokemonList(page: Int, offset: Int): Flow<PagingData<PokemonResponse>>
}