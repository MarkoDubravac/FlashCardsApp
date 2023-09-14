package com.example.flashcardsapp.ui.play.di

import com.example.flashcardsapp.ui.play.PlayViewModel
import com.example.flashcardsapp.ui.play.mapper.PlayMapper
import com.example.flashcardsapp.ui.play.mapper.PlayMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val playModule = module {
    viewModel { id ->
        PlayViewModel(
            deckRepository = get(),
            playMapper = get(),
            deckId = id[0]
        )
    }
    single<PlayMapper> { PlayMapperImpl() }
}
