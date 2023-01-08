package com.example.flashcardsapp.ui.deckDetails

data class DeckDetailsViewState(
    val id: Int, val name: String, val size: Int, val cards: List<CardViewState>
)
