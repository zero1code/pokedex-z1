package com.z1.pokedex.core.network.services.pokedex

import android.graphics.drawable.Drawable
import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.core.network.model.PokemonPageDto

interface PokedexClient {
    suspend fun fetchPokemonPage(page: Int): PokemonPageDto
    suspend fun fetchPokemonImage(imageUrl: String): Drawable?
    suspend fun fetchPokemonDetails(pokemonName: String): PokemonDetailsDTO
}