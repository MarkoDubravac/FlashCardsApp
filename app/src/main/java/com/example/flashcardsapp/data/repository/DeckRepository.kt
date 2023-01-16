package com.example.flashcardsapp.data.repository

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.model.PlayingCard
import kotlinx.coroutines.flow.Flow

interface DeckRepository {

    fun decks(): Flow<List<Deck>>

    fun deckDetails(deckId: Int): Flow<DeckDetails>

    fun favoriteDecks(): Flow<List<Deck>>

    fun cards(): Flow<List<PlayingCard>>

    suspend fun addDeckToFavorites(deckId: Int)

    suspend fun removeDeckFromFavorites(deckId: Int)

    suspend fun toggleFavorites(deckId: Int)

    suspend fun insertDeck(name: String)

    suspend fun deleteDeck(deckId: Int)

    suspend fun insertCard(question: String, answer: String, deckId: Int)

    suspend fun deleteCard(cardId: Int)

    suspend fun changeIsAnswered(cardId: Int, isAnswered: Boolean)

    suspend fun changeIsLearned(cardId: Int, isLearned: Boolean)

}
