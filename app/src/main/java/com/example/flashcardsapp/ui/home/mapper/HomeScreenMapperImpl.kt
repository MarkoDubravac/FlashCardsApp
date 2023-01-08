package com.example.flashcardsapp.ui.home.mapper

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.ui.component.DeckCardViewState
import com.example.flashcardsapp.ui.home.HomeDeckViewState
import com.example.flashcardsapp.ui.home.HomeViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeViewState(decks: List<Deck>) =
        HomeViewState(homeDecks = decks.map { toHomeMovieViewState(it) })

    private fun toHomeMovieViewState(homeDeck: Deck) = HomeDeckViewState(
        id = homeDeck.id,
        deckCardViewState = DeckCardViewState(
            deckId = homeDeck.id,
            name = homeDeck.name,
            size = homeDeck.size,
            isFavorite = homeDeck.isFavorite,
        ),
    )
}
