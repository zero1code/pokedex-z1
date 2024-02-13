package com.z1.pokedex.core.network.repository.pokemonlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.z1.pokedex.core.network.PokedexApi
import com.z1.pokedex.core.network.datasource.PokemonDataSource
import com.z1.pokedex.core.network.model.PokemonResponse
import com.z1.pokedex.core.network.util.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(
    private val api: PokedexApi
) : PokemonRepository {
    override fun fetchPokemonList(page: Int, offset: Int): Flow<PagingData<PokemonResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { PokemonDataSource(api) }
        ).flow
    }
}