package com.z1.pokedex.core.di.network

import com.z1.pokedex.core.common.shared.domain.repository.PokemonImageRepository
import com.z1.pokedex.core.common.shared.data.repository.PokemonImageRepositoryImpl
import org.koin.dsl.module

private val providePokemonImageRepository = module {
    single<PokemonImageRepository>{ PokemonImageRepositoryImpl(get()) }
}

val networkRepositoryModule = listOf(
    providePokemonImageRepository
)