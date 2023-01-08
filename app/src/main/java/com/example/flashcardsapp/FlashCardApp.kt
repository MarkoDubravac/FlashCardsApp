package com.example.flashcardsapp

import android.app.Application
import com.example.flashcardsapp.data.di.dataModule
import com.example.flashcardsapp.ui.deckDetails.di.deckDetailsModule
import com.example.flashcardsapp.ui.favorites.di.favoritesModule
import com.example.flashcardsapp.ui.home.di.homeModule
import org.koin.core.context.startKoin

class FlashCardApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(homeModule, favoritesModule, deckDetailsModule, dataModule)
        }
    }
}
