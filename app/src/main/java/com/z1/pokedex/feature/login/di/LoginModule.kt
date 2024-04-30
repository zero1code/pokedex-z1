package com.z1.pokedex.feature.login.di

import com.z1.pokedex.feature.login.presentation.screen.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideLoginViewModel = module {
    viewModel { LoginViewModel(get()) }
}

val loginModule = listOf(
    provideLoginViewModel
)