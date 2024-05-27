package com.z1.pokedex.core.di.feature

import com.z1.pokedex.feature.home.data.datasource.local.PokemonLocalDataSource
import com.z1.pokedex.feature.home.data.datasource.local.PokemonLocalDataSourceImpl
import com.z1.pokedex.feature.home.data.datasource.remote.PokemonRemoteDataSource
import com.z1.pokedex.feature.home.data.datasource.remote.PokemonRemoteDataSourceImpl
import com.z1.pokedex.feature.home.data.repository.PokemonRepositoryImpl
import com.z1.pokedex.feature.home.domain.repository.PokemonRepository
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCaseImpl
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideHomeViewModel = module {
    viewModel { HomeViewModel(get(), get()) }
}

private val providePokemonUseCase = module {
    single<PokemonUseCase> { PokemonUseCaseImpl(get(), get()) }
}

private val providePokemonLocalDataSource = module {
    single<PokemonLocalDataSource> { PokemonLocalDataSourceImpl(get()) }
}

private val providePokemonRemoteDataSource = module {
    single<PokemonRemoteDataSource> { PokemonRemoteDataSourceImpl(get()) }
}

private val providePokemonRepository = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get(), get()) }
}

val homeScreenModule = listOf(
    provideHomeViewModel,
    providePokemonUseCase,
    providePokemonLocalDataSource,
    providePokemonRemoteDataSource,
    providePokemonRepository
)