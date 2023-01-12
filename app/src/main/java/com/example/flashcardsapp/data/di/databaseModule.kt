package com.example.flashcardsapp.data.di

import androidx.room.Room
import com.example.flashcardsapp.data.database.DecksAppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "app_database.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            DecksAppDatabase::class.java,
            APP_DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()
    }
    single{
        get<DecksAppDatabase>().deckDao()
    }
}

