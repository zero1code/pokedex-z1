package com.z1.pokedex.core.network

import com.z1.pokedex.core.network.model.PokemonDTO
import com.z1.pokedex.core.network.model.PokemonResponse
import com.z1.pokedex.core.network.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("offset") offset: Int = Constants.POKEMON_STARTING_OFFSET,
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") name: String?
    ): Response<PokemonDTO>
}