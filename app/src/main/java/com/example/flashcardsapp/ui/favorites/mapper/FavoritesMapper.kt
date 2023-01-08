package com.example.flashcardsapp.ui.favorites.mapper

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.ui.favorites.FavoritesViewState

interface FavoritesMapper {
    fun toFavoritesViewState(favoriteDecks: List<Deck>): FavoritesViewState
}
