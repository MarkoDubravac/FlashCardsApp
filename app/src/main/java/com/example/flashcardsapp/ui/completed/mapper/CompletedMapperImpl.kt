package com.example.flashcardsapp.ui.completed.mapper

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.ui.completed.CompletedDeckViewState
import com.example.flashcardsapp.ui.completed.CompletedViewState
import com.example.flashcardsapp.ui.component.DeckCardViewState

class CompletedMapperImpl : CompletedMapper {
    override fun toCompletedViewState(completedDecks: List<Deck>) =
        CompletedViewState(completedDecks = completedDecks.map { toCompletedDeckViewState(it) })

    private fun toCompletedDeckViewState(completedDeck: Deck) = CompletedDeckViewState(
        id = completedDeck.id, deckCardViewState = DeckCardViewState(
            deckId = completedDeck.id,
            name = completedDeck.name,
            size = completedDeck.size,
            isFavorite = completedDeck.isFavorite,
        )
    )
}
