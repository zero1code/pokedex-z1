package com.z1.pokedex.core.di.feature

import androidx.activity.ComponentActivity
import com.z1.pokedex.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSource
import com.z1.pokedex.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSourceImpl
import com.z1.pokedex.feature.subscription.data.repository.SubscriptionRepositoryImpl
import com.z1.pokedex.feature.subscription.domain.repository.SubscriptionRepository
import com.z1.pokedex.feature.subscription.presentation.screen.viewmodel.SubscriptionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

private val provideSubscriptionViewModel = module {
    viewModel { (activity: ComponentActivity) ->
        SubscriptionViewModel(get { parametersOf(activity) }, get())
    }
}

private val provideSubscriptionRemoteDataSource = module {
    single<SubscriptionRemoteDataSource>{ SubscriptionRemoteDataSourceImpl(get()) }
}

private val provideSubscriptionRepository = module {
    single<SubscriptionRepository>{ SubscriptionRepositoryImpl(get()) }
}

val subscriptionModule = listOf(
    provideSubscriptionViewModel,
    provideSubscriptionRemoteDataSource,
    provideSubscriptionRepository
)