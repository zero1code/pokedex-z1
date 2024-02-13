package com.z1.pokedex.core.network.di

import com.z1.pokedex.core.network.PokedexApi
import com.z1.pokedex.core.network.datasource.PokemonDataSource
import com.z1.pokedex.core.network.repository.pokemonlist.PokemonRepository
import com.z1.pokedex.core.network.repository.pokemonlist.PokemonRepositoryImpl
import com.z1.pokedex.core.network.util.Constants
import com.z1.pokedex.core.network.util.NullOrEmptyConverterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private val provideApi = module {
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

private val providePokemonDataSource = module {
    single { PokemonDataSource(get()) }
}

private val providePokemonRepository = module {
    single<PokemonRepository>{ PokemonRepositoryImpl(get()) }
}

val networkModule = listOf(
    provideApi,
    providePokemonDataSource,
    providePokemonRepository
)