package com.example.flashcardsapp.ui.home.di

import com.example.flashcardsapp.ui.home.HomeViewModel
import com.example.flashcardsapp.ui.home.mapper.HomeScreenMapper
import com.example.flashcardsapp.ui.home.mapper.HomeScreenMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            deckRepository = get(),
            homeScreenMapper = get(),
        )
    }
    single<HomeScreenMapper> { HomeScreenMapperImpl() }
}
