package com.z1.pokedex.core.database.di

import androidx.room.Room
import com.z1.pokedex.core.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

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

val databaseModule = listOf(
    provideRoomDatabase,
    providePokemonDao
)