package com.example.flashcardsapp.ui.completed

import com.example.flashcardsapp.ui.component.DeckCardViewState

class CompletedDeckViewState(
    val id: Int,
    val deckCardViewState: DeckCardViewState,
)

class CompletedViewState(
    val completedDecks: List<CompletedDeckViewState>
)
