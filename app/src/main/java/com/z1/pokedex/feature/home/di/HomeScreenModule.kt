package com.z1.pokedex.feature.home.di

import com.z1.pokedex.feature.home.domain.mapper.PokemonDetailsMapper
import com.z1.pokedex.feature.home.domain.mapper.PokemonMapper
import com.z1.pokedex.feature.home.domain.mapper.PokemonPageMapper
import com.z1.pokedex.feature.home.domain.mapper.PokemonTypeMapper
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCaseImpl
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideHomeViewModel = module {
    viewModel { HomeViewModel(get()) }
}

private val providePokemonUseCase = module {
    single<PokemonUseCase> { PokemonUseCaseImpl(get()) }
}

private val providePokemonMapper = module {
    single<PokemonMapper> { PokemonMapper() }
}

private val providePokemonPageMapper = module {
    single<PokemonPageMapper> { PokemonPageMapper(get()) }
}

private val providePokemonDetailsMapper = module {
    single<PokemonDetailsMapper> { PokemonDetailsMapper(get()) }
}

private val providePokemonTypeMapper = module {
    single<PokemonTypeMapper> { PokemonTypeMapper() }
}


val homeScreenModule = listOf(
    provideHomeViewModel,
    providePokemonUseCase,
    providePokemonMapper,
    providePokemonPageMapper,
    providePokemonDetailsMapper,
    providePokemonTypeMapper
)
