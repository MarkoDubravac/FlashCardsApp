package com.example.flashcardsapp.ui.home

import com.example.flashcardsapp.ui.component.DeckCardViewState

class HomeDeckViewState(
    val id: Int, val deckCardViewState: DeckCardViewState
)

class HomeViewState(
    val homeDecks: List<HomeDeckViewState>
)
