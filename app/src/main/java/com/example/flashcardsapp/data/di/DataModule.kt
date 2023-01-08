package com.example.flashcardsapp.data.di

import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.data.repository.FakeDeckRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<DeckRepository> {
        FakeDeckRepository(ioDispatcher = Dispatchers.IO)
    }
}
