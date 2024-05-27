package com.z1.pokedex.core.di.network

import androidx.activity.ComponentActivity
import com.google.android.gms.auth.api.identity.Identity
import com.z1.pokedex.BuildConfig
import com.z1.pokedex.core.network.services.googleauth.GoogleAuthClient
import com.z1.pokedex.core.network.services.googleauth.GoogleAuthClientImpl
import com.z1.pokedex.core.network.services.googlebilling.GoogleBillingClient
import com.z1.pokedex.core.network.services.googlebilling.GoogleBillingClientImpl
import com.z1.pokedex.core.network.services.pokedex.PokedexApi
import com.z1.pokedex.core.network.services.pokedex.PokedexClient
import com.z1.pokedex.core.network.services.pokedex.PokedexClientImpl
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
    single<PokedexClient> { PokedexClientImpl(androidContext(), get()) }
}

private val provideGoogleAuthClient = module {
    single<GoogleAuthClient> { GoogleAuthClientImpl(Identity.getSignInClient(androidContext())) }
}

private val provideGoogleBillingClient = module {
    single<GoogleBillingClient> { (activity: ComponentActivity) -> GoogleBillingClientImpl(activity) }
}

val networkModule = listOf(
    provideApi,
    providePokedexClient,
    provideGoogleAuthClient,
    provideGoogleBillingClient
)