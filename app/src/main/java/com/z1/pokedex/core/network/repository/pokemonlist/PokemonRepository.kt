package com.z1.pokedex.core.network.repository.pokemonlist

import android.graphics.drawable.Drawable
import com.z1.pokedex.feature.home.presentation.model.PokemonPage
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage>
    suspend fun fetchPokemonImage(imageUrl: String): Drawable?
}