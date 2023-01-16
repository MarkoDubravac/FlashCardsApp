package com.example.flashcardsapp.data.repository

import com.example.flashcardsapp.mock.FavoritesDBMock
import com.example.flashcardsapp.mock.MockDecks
import com.example.flashcardsapp.mock.MockDecks.getDecksList
import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.model.PlayingCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

@Suppress("OPT_IN_IS_NOT_ENABLED")
class FakeDeckRepository(
    //private val deckDao: DeckDao,
    private val ioDispatcher: CoroutineDispatcher,
) : DeckRepository {

    private val fakeDecks = getDecksList().toMutableList()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val decks: Flow<List<Deck>> = FavoritesDBMock.favoriteIds.mapLatest { favoriteIds ->
        fakeDecks.map { deck ->
            if (favoriteIds.contains(deck.id)) deck.copy(isFavorite = true)
            else deck.copy(isFavorite = false)
        }
    }.flowOn(ioDispatcher)

    override fun decks(): Flow<List<Deck>> = decks

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun deckDetails(deckId: Int): Flow<DeckDetails> =
        FavoritesDBMock.favoriteIds.mapLatest { favoriteIds ->
            val deckDetails = MockDecks.getDeckDetails(deckId)
            if (favoriteIds.contains(deckDetails.deck.id)) deckDetails.copy(
                deck = deckDetails.deck.copy(isFavorite = true)
            )
            else deckDetails.copy(deck = deckDetails.deck.copy(isFavorite = false))
        }.flowOn(ioDispatcher)

    override fun favoriteDecks(): Flow<List<Deck>> = decks.map { deck ->
        deck.filter { fakeDeck -> fakeDeck.isFavorite }
    }

    override fun cards(): Flow<List<PlayingCard>> {
        TODO("Not yet implemented")
    }

    override suspend fun addDeckToFavorites(deckId: Int) {
        FavoritesDBMock.insert(deckId = deckId)
    }

    override suspend fun removeDeckFromFavorites(deckId: Int) {
        FavoritesDBMock.delete(deckId = deckId)
    }

    override suspend fun toggleFavorites(deckId: Int) =
        if (FavoritesDBMock.favoriteIds.value.contains(deckId)) {
            removeDeckFromFavorites(deckId = deckId)
        } else {
            addDeckToFavorites(deckId = deckId)
        }

    override suspend fun insertDeck(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDeck(deckId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCard(question: String, answer: String, deckId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCard(cardId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun changeIsAnswered(cardId: Int, isAnswered: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun changeIsLearned(cardId: Int, isLearned: Boolean) {
        TODO("Not yet implemented")
    }


}
