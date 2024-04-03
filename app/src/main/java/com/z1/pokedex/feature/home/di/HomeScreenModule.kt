package com.z1.pokedex.feature.home.di

import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCaseImpl
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideHomeViewModel = module {
    viewModel { HomeViewModel(get()) }
}

private val providePokemonUseCase = module {
    single<PokemonUseCase>{ PokemonUseCaseImpl(get()) }
}

val homeScreenModule = listOf(
    provideHomeViewModel,
    providePokemonUseCase
)
