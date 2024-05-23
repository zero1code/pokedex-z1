package com.z1.pokedex.feature.home.data.datasource.remote

import com.z1.pokedex.core.network.model.PokemonDTO

interface PokemonRemoteDataSource {
    suspend fun fetchPokemonPage(page: Int): List<PokemonDTO>
}