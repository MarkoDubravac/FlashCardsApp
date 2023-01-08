package com.example.flashcardsapp.ui.favorites

import com.example.flashcardsapp.ui.component.DeckCardViewState

class FavoriteDeckViewState(
    val id: Int,
    val deckCardViewState: DeckCardViewState,
)

class FavoritesViewState(
    val favoriteDecks: List<FavoriteDeckViewState>
)
