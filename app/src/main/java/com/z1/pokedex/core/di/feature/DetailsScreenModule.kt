package com.z1.pokedex.core.di.feature

import com.z1.pokedex.feature.details.data.datasource.local.PokemonDetailsLocalDataSource
import com.z1.pokedex.feature.details.data.datasource.local.PokemonDetailsLocalDataSourceImpl
import com.z1.pokedex.feature.details.data.datasource.remote.PokemonDetailsRemoteDataSource
import com.z1.pokedex.feature.details.data.datasource.remote.PokemonDetailsRemoteDataSourceImpl
import com.z1.pokedex.feature.details.data.repository.PokemonDetailsRepositoryImpl
import com.z1.pokedex.feature.details.domain.repository.PokemonDetailsRepository
import com.z1.pokedex.feature.details.presentation.screen.viewmodel.PokemonDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideDetailsViewModel = module {
    viewModel { PokemonDetailsViewModel(get(), get(), get()) }
}

private val providePokemonDetailsLocalDataSource = module {
    single<PokemonDetailsLocalDataSource>{ PokemonDetailsLocalDataSourceImpl(get()) }
}

private val providePokemonDetailsRemoteDataSource = module {
    single<PokemonDetailsRemoteDataSource>{ PokemonDetailsRemoteDataSourceImpl(get()) }
}

private val providePokemonDetailsRepository = module {
    single<PokemonDetailsRepository>{ PokemonDetailsRepositoryImpl(get(), get()) }
}

val detailsScreenModule = listOf(
    provideDetailsViewModel,
    providePokemonDetailsLocalDataSource,
    providePokemonDetailsRemoteDataSource,
    providePokemonDetailsRepository
)