package com.z1.pokedex.core.network.services.pokedex

import android.content.Context
import coil.Coil
import coil.request.ImageRequest
import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.core.network.model.PokemonPageDto
import com.z1.pokedex.core.network.util.Constants

class PokedexClientImpl(
    private val context: Context,
    private val api: PokedexApi
): PokedexClient {
    override suspend fun fetchPokemonPage(page: Int): PokemonPageDto {
        return api.fetchPokemonPage(page * Constants.PAGE_SIZE, Constants.PAGE_SIZE)
                .takeIf { it.isSuccessful }
                ?.body().takeIf { it?.nextPage != null }
                ?: throw Exception("Response data is null")
    }

    override suspend fun fetchPokemonImage(imageUrl: String) =
        Coil.imageLoader(context).execute(ImageRequest.Builder(context).data(imageUrl).build()).drawable

    override suspend fun fetchPokemonDetails(pokemonName: String): PokemonDetailsDTO {
        return api.fetchPokemonDetails(pokemonName)
            .takeIf { it.isSuccessful }
            ?.body()
            ?: throw Exception("Pokemon details is null")
    }
}