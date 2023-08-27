package com.example.flashcardsapp.ui.completed.di

import com.example.flashcardsapp.ui.completed.CompletedViewModel
import com.example.flashcardsapp.ui.completed.mapper.CompletedMapper
import com.example.flashcardsapp.ui.completed.mapper.CompletedMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val completedModule = module {
    viewModel {
        CompletedViewModel(
            deckRepository = get(),
            completedMapper = get(),
        )
    }
    single<CompletedMapper> { CompletedMapperImpl() }
}
