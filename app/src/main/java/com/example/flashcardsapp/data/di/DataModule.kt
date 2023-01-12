package com.example.flashcardsapp.data.di

import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.data.repository.DeckRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<DeckRepository> {
        DeckRepositoryImpl(
            deckDao = get(),
            bgDispatcher = Dispatchers.IO
        )
    }
}
