package com.z1.pokedex.core.datasource.di


import com.z1.pokedex.core.datasource.mapper.PokemonMapper
import com.z1.pokedex.core.datasource.mapper.PokemonPageMapper
import com.z1.pokedex.core.datasource.repository.PokemonRepository
import com.z1.pokedex.core.datasource.repository.PokemonRepositoryImpl
import org.koin.dsl.module

private val providePokemonRepository = module {
    single<PokemonRepository>{ PokemonRepositoryImpl(get(), get()) }
}

private val providePokemonMapper = module {
    single<PokemonMapper>{ PokemonMapper() }
}

private val providePokemonPageMapper = module {
    single<PokemonPageMapper>{ PokemonPageMapper(get()) }
}

val datasourceModule = listOf(
    providePokemonRepository,
    providePokemonMapper,
    providePokemonPageMapper,
)