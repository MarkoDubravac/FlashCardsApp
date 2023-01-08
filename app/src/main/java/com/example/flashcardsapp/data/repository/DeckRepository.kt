package com.example.flashcardsapp.data.repository

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.model.DeckDetails
import kotlinx.coroutines.flow.Flow

interface DeckRepository {

    fun decks(): Flow<List<Deck>>

    fun deckDetails(deckId: Int): Flow<DeckDetails>

    fun favoriteDecks(): Flow<List<Deck>>

    suspend fun addDeckToFavorites(deckId: Int)

    suspend fun removeDeckFromFavorites(deckId: Int)

    suspend fun toggleFavorites(deckId: Int)
}
