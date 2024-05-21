package com.z1.pokedex.core.common.shared.di

import androidx.activity.ComponentActivity
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val provideUserDataViewModel = module {
    viewModel {
        UserDataViewModel(get(), get())
    }
}

val sharedModule = listOf(
    provideUserDataViewModel
)