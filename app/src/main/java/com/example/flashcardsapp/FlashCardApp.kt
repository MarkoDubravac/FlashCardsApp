package com.example.flashcardsapp

import android.app.Application
import com.example.flashcardsapp.data.di.dataModule
import com.example.flashcardsapp.data.di.databaseModule
import com.example.flashcardsapp.data.di.firebaseAuthModule
import com.example.flashcardsapp.ui.completed.di.completedModule
import com.example.flashcardsapp.ui.deckDetails.di.deckDetailsModule
import com.example.flashcardsapp.ui.favorites.di.favoritesModule
import com.example.flashcardsapp.ui.home.di.homeModule
import com.example.flashcardsapp.ui.signIn.di.signInModule
import com.example.flashcardsapp.ui.signUp.di.signUpModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlashCardApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FlashCardApp)
            modules(
                signUpModule,
                signInModule,
                homeModule,
                favoritesModule,
                deckDetailsModule,
                completedModule,
                firebaseAuthModule,
                dataModule,
                databaseModule
            )
        }
    }
}
