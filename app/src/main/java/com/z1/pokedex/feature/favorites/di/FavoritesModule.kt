package com.z1.pokedex.feature.favorites.di

import com.z1.pokedex.feature.favorites.domain.usecase.PokemonFavoriteUseCase
import com.z1.pokedex.feature.favorites.domain.usecase.PokemonFavoriteUseCaseImpl
import com.z1.pokedex.feature.favorites.presentation.screen.viewmodel.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideFavoritesViewModel = module {
    viewModel { FavoritesViewModel(get(), get(), get()) }
}

private val providePokemonFavoriteUseCase = module {
    single<PokemonFavoriteUseCase>{ PokemonFavoriteUseCaseImpl(get()) }
}

val favoritesModule = listOf(
    provideFavoritesViewModel,
    providePokemonFavoriteUseCase
)