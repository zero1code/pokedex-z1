package com.z1.pokedex.core.datasource.di


import com.z1.pokedex.core.datasource.repository.PokemonRepository
import com.z1.pokedex.core.datasource.repository.PokemonRepositoryImpl
import com.z1.pokedex.core.network.service.PokedexClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val providePokemonRepository = module {
    single<PokemonRepository>{ PokemonRepositoryImpl(get(), get()) }
}

private val providePokedexClient = module {
    single { PokedexClient(androidContext(), get()) }
}

val datasourceModule = listOf(
    providePokemonRepository,
    providePokedexClient
)