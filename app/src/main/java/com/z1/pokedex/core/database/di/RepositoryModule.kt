package com.z1.pokedex.core.database.di

import com.z1.pokedex.core.database.repository.favorites.PokemonFavoriteRepository
import com.z1.pokedex.core.database.repository.favorites.PokemonFavoriteRepositoryImpl
import org.koin.dsl.module

private val providePokemonFavoriteRepository = module {
    single<PokemonFavoriteRepository>{ PokemonFavoriteRepositoryImpl(get(), get()) }
}

val databaseRepositoryModule = listOf(
    providePokemonFavoriteRepository
)