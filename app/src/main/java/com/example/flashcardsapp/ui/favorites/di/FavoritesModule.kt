package com.example.flashcardsapp.ui.favorites.di

import com.example.flashcardsapp.ui.favorites.FavoritesViewModel
import com.example.flashcardsapp.ui.favorites.mapper.FavoritesMapper
import com.example.flashcardsapp.ui.favorites.mapper.FavoritesMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(
            deckRepository = get(),
            favoritesMapper = get(),
        )
    }
    single<FavoritesMapper> { FavoritesMapperImpl() }
}
