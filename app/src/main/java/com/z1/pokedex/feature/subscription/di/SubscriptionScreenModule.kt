package com.z1.pokedex.feature.subscription.di

import androidx.activity.ComponentActivity
import com.z1.pokedex.feature.subscription.presentation.screen.viewmodel.SubscriptionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

private val provideSubscriptionViewModel = module {
    viewModel { (activity: ComponentActivity) ->
        SubscriptionViewModel(get { parametersOf(activity) }, get())
    }
}

val subscriptionModule = listOf(
    provideSubscriptionViewModel
)