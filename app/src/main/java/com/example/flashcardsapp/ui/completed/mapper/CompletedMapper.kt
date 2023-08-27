package com.example.flashcardsapp.ui.completed.mapper

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.ui.completed.CompletedViewState

interface CompletedMapper {
    fun toCompletedViewState(completedDecks: List<Deck>): CompletedViewState
}
