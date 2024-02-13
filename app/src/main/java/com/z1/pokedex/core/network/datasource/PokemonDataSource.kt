package com.z1.pokedex.core.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.z1.pokedex.core.network.PokedexApi
import com.z1.pokedex.core.network.model.PokemonResponse

class PokemonDataSource(
    private val api: PokedexApi
): PagingSource<Int, PokemonResponse>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonResponse>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResponse> {
        TODO("Not yet implemented")
    }
}