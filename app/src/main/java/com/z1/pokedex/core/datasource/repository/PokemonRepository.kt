package com.z1.pokedex.core.datasource.repository

import android.graphics.drawable.Drawable
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    suspend fun fetchPokemonPage(page: Int): Flow<List<Pokemon>>
    suspend fun fetchPokemonImage(imageUrl: String): Drawable?
    suspend fun fetchPokemonDetails(pokemonName: String): Flow<PokemonDetails>
}