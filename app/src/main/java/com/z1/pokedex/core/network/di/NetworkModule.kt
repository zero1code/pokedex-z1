package com.z1.pokedex.core.network.di

import com.google.android.gms.auth.api.identity.Identity
import com.z1.pokedex.BuildConfig
import com.z1.pokedex.core.network.service.pokedex.PokedexApi
import com.z1.pokedex.core.network.service.pokedex.PokedexClient
import com.z1.pokedex.core.network.service.connectivity.ConnectivityService
import com.z1.pokedex.core.network.service.connectivity.ConnectivityServiceImpl
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClientImpl
import com.z1.pokedex.core.network.util.Constants
import com.z1.pokedex.core.network.util.NullOrEmptyConverterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private val provideApi = module {
    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG)
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            .build()
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(NullOrEmptyConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(PokedexApi::class.java)
    }
}

private val providePokedexClient = module {
    single { PokedexClient(androidContext(), get()) }
}

private val provideGoogleAuthClient = module {
    factory<GoogleAuthClient>{ GoogleAuthClientImpl(androidContext(), Identity.getSignInClient(androidContext())) }
}

private val provideConnectionRepository = module {
    single<ConnectivityService>{ ConnectivityServiceImpl(androidContext()) }
}

val networkModule = listOf(
    provideApi,
    providePokedexClient,
    provideGoogleAuthClient,
    provideConnectionRepository
)