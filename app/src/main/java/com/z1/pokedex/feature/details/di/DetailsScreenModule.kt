package com.z1.pokedex.feature.details.di

import com.z1.pokedex.feature.details.screen.viewmodel.PokemonDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideDetailsViewModel = module {
    viewModel { PokemonDetailsViewModel(get(), get(), get(), get()) }
}

val detailsScreenModule = listOf(
    provideDetailsViewModel
)