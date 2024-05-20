package com.z1.pokedex.core.network.di

import com.z1.pokedex.core.network.service.pokedex.repository.PokemonDetailsRepository
import com.z1.pokedex.core.network.service.pokedex.repository.PokemonDetailsRepositoryImpl
import org.koin.dsl.module

private val providePokemonDetailsRepository = module {
    single<PokemonDetailsRepository>{ PokemonDetailsRepositoryImpl(get()) }
}

val networkRepositoryModule = listOf(
    providePokemonDetailsRepository
)