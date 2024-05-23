package com.z1.pokedex.core.di.database

import androidx.room.Room
import com.z1.pokedex.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.math.sin

private val provideRoomDatabase = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "db.pokedexz1"
        ).addMigrations().build()
    }
}


private val providePokemonDao = module {
    single { get<AppDatabase>().getPokemonDao() }
}

private val providePokemonFavoriteDao = module {
    single { get<AppDatabase>().getPokemonFavoriteDao() }
}

val databaseModule = listOf(
    provideRoomDatabase,
    providePokemonDao,
    providePokemonFavoriteDao
)