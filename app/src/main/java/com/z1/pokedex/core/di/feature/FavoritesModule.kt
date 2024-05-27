package com.z1.pokedex.core.di.feature

import com.z1.pokedex.feature.favorites.data.datasource.local.PokemonFavoriteLocalDataSource
import com.z1.pokedex.feature.favorites.data.datasource.local.PokemonFavoriteLocalDataSourceImpl
import com.z1.pokedex.feature.favorites.data.repository.PokemonFavoriteRepositoryImpl
import com.z1.pokedex.feature.favorites.domain.repository.PokemonFavoriteRepository
import com.z1.pokedex.feature.favorites.domain.usecase.PokemonFavoriteUseCase
import com.z1.pokedex.feature.favorites.domain.usecase.PokemonFavoriteUseCaseImpl
import com.z1.pokedex.feature.favorites.presentation.screen.viewmodel.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideFavoritesViewModel = module {
    viewModel { FavoritesViewModel(get(), get()) }
}

private val providePokemonFavoriteUseCase = module {
    single<PokemonFavoriteUseCase> { PokemonFavoriteUseCaseImpl(get(), get()) }
}

private val providePokemonFavoriteLocalDataSource = module {
    single<PokemonFavoriteLocalDataSource> { PokemonFavoriteLocalDataSourceImpl(get()) }
}

private val providePokemonFavoriteRepository = module {
    single<PokemonFavoriteRepository> { PokemonFavoriteRepositoryImpl(get(), get()) }
}

val favoritesModule = listOf(
    provideFavoritesViewModel,
    providePokemonFavoriteUseCase,
    providePokemonFavoriteLocalDataSource,
    providePokemonFavoriteRepository
)