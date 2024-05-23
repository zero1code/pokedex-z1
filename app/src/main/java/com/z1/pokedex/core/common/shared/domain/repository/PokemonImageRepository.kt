package com.z1.pokedex.core.common.shared.domain.repository

import android.graphics.drawable.Drawable

interface PokemonImageRepository {
    suspend fun fetchPokemonImage(imageUrl: String): Drawable?
}