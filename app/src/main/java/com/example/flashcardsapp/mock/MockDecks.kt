package com.example.flashcardsapp.mock

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.model.PlayingCard

object MockDecks {

    fun getDecksList(): List<Deck> = listOf(
        Deck(
            id = 1,
            name = "Deck1",
            size = 5,
            isFavorite = true,
        ), Deck(
            id = 2, name = "Deck2", size = 11, isFavorite = false
        ), Deck(
            id = 3,
            name = "Deck3",
            size = 54,
            isFavorite = true,
        ), Deck(
            id = 4, name = "Deck4", size = 9, isFavorite = false
        ), Deck(
            id = 5, name = "Deck5", size = 97, isFavorite = false
        )
    )

    fun getDeckDetails(): DeckDetails = DeckDetails(Deck(
        id = 5, name = "Deck5", size = 6, isFavorite = false
    ), cards = List(6) {
        PlayingCard(
            id = it,
            question = "How much does the earth cost?",
            answer = "Idk",
            isAnswered = false,
            isLearned = false,
        )
    })

    fun getDeckDetails(deckId: Int): DeckDetails = DeckDetails(
        deck = getDecksList().first { it.id == deckId },
        cards = getDeckDetails().cards,
    )
}
