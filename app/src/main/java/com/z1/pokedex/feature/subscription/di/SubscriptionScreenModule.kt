package com.z1.pokedex.feature.subscription.di

import com.z1.pokedex.feature.subscription.presentation.screen.viewmodel.SubscriptionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideSubscriptionViewModel = module {
    viewModel { SubscriptionViewModel(get()) }
}

val subscriptionModule = listOf(
    provideSubscriptionViewModel
)