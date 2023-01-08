package com.example.flashcardsapp.ui.favorites.mapper

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.ui.component.DeckCardViewState
import com.example.flashcardsapp.ui.favorites.FavoriteDeckViewState
import com.example.flashcardsapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteDecks: List<Deck>) =
        FavoritesViewState(favoriteDecks = favoriteDecks.map { toFavoritesDeckViewState(it) })

    private fun toFavoritesDeckViewState(favoriteDeck: Deck) = FavoriteDeckViewState(
        id = favoriteDeck.id, deckCardViewState = DeckCardViewState(
            deckId = favoriteDeck.id,
            name = favoriteDeck.name,
            size = favoriteDeck.size,
            isFavorite = favoriteDeck.isFavorite,
        )
    )
}
