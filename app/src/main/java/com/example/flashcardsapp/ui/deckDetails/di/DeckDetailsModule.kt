package com.example.flashcardsapp.ui.deckDetails.di

import com.example.flashcardsapp.ui.deckDetails.DeckDetailsViewModel
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapper
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val deckDetailsModule = module {
    viewModel { id ->
        DeckDetailsViewModel(
            deckRepository = get(),
            deckDetailsMapper = get(),
            deckId = id[0],
        )
    }
    single<DeckDetailsMapper> { DeckDetailsMapperImpl() }
}
