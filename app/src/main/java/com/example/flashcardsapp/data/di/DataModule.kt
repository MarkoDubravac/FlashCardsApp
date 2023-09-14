package com.example.flashcardsapp.data.di

import com.example.flashcardsapp.data.repository.AuthRepository
import com.example.flashcardsapp.data.repository.AuthRepositoryImpl
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.data.repository.DeckRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    factory<DeckRepository> {
        DeckRepositoryImpl(
            deckDao = get(), bgDispatcher = Dispatchers.IO, FirebaseAuth.getInstance()
        )
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuth = get()
        )
    }
}

