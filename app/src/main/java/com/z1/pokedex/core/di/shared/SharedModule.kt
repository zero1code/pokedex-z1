package com.z1.pokedex.core.di.shared

import androidx.activity.ComponentActivity
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityServiceImpl
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

private val provideUserDataViewModel = module {
    viewModel { (activity: ComponentActivity) ->
        UserDataViewModel(get(), get { parametersOf(activity) }, get())
    }
}

private val provideConnectionService = module {
    single<ConnectivityService>{ ConnectivityServiceImpl(androidContext()) }
}

val sharedModule = listOf(
    provideUserDataViewModel,
    provideConnectionService
)