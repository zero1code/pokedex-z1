package com.z1.pokedex.core.datasource.di


import com.z1.pokedex.core.datasource.repository.PokemonRepository
import com.z1.pokedex.core.datasource.repository.PokemonRepositoryImpl
import org.koin.dsl.module

private val providePokemonRepository = module {
    single<PokemonRepository>{ PokemonRepositoryImpl(get(), get()) }
}

val datasourceModule = listOf(
    providePokemonRepository
)