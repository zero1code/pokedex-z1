package com.z1.pokedex.core.network.service.pokedex

import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.core.network.model.PokemonPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon")
    suspend fun fetchPokemonPage(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonPageDto>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonDetails(
        @Path("name") name: String
    ): Response<PokemonDetailsDTO>
}