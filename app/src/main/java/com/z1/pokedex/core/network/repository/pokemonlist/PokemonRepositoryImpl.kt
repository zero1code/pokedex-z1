package com.z1.pokedex.core.network.repository.pokemonlist

import android.content.Context
import android.graphics.drawable.Drawable
import coil.Coil
import coil.request.ImageRequest
import com.z1.pokedex.core.datasource.PokemonPageDataSource
import com.z1.pokedex.feature.home.presentation.model.PokemonPage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRepositoryImpl(
    private val context: Context,
    private val pokemonPageDataSource: PokemonPageDataSource,
    private val dispatcher: CoroutineDispatcher
) : PokemonRepository {
    override suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage> =
        flow { emit(pokemonPageDataSource.fetchPokemonPage(page)) }
            .flowOn(dispatcher)

    override suspend fun fetchPokemonImage(imageUrl: String): Drawable? {
        val result = Coil.imageLoader(context).execute(ImageRequest.Builder(context).data(imageUrl).build()).drawable
        return result
    }
}