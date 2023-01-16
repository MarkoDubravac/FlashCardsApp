@file:Suppress("OPT_IN_IS_NOT_ENABLED") @file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.flashcardsapp.data.repository

import com.example.flashcardsapp.data.database.DbDeck
import com.example.flashcardsapp.data.database.DbFavoriteDeck
import com.example.flashcardsapp.data.database.DbPlayingCard
import com.example.flashcardsapp.data.database.DeckDao
import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.model.PlayingCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

class DeckRepositoryImpl(
    private val deckDao: DeckDao,
    private val bgDispatcher: CoroutineDispatcher,
) : DeckRepository {

    private val decks = deckDao.getDecks().map {
        it.map { dbDeck ->
            Deck(
                id = dbDeck.id,
                name = dbDeck.name,
                size = getDeckSize(dbDeck.id).first(),
                isFavorite = dbDeck.isFavorite
            )
        }
    }.flatMapLatest { decks ->
        deckDao.getFavorites().map { favoriteDecks ->
            decks.map { deck ->
                Deck(
                    isFavorite = favoriteDecks.any { it.id == deck.id },
                    id = deck.id,
                    size = getDeckSize(deck.id).first(),
                    name = deck.name
                )
            }
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher), started = SharingStarted.Eagerly, replay = 1
    )

    private val cards = deckDao.getCards().map {
        it.map { dbPlayingCard ->
            PlayingCard(
                id = dbPlayingCard.id,
                question = dbPlayingCard.question,
                answer = dbPlayingCard.answer,
                isLearned = dbPlayingCard.isLearned,
                isAnswered = dbPlayingCard.isAnswered,
                deckId = dbPlayingCard.deckId
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher), started = SharingStarted.Eagerly, replay = 1
    )

    override fun decks(): Flow<List<Deck>> = decks

    override suspend fun insertDeck(name: String) {
        runBlocking(bgDispatcher) {
            deckDao.insertDeck(
                dbDeck = DbDeck(
                    name = name, size = 0, isFavorite = false
                )
            )
        }
    }

    override suspend fun deleteDeck(deckId: Int) {
        runBlocking(bgDispatcher) {
            deckDao.deleteDeck(id = deckId)
            deckDao.deleteFavorite(id = deckId)
        }
    }

    override suspend fun insertCard(question: String, answer: String, deckId: Int) {
        runBlocking(bgDispatcher) {
            deckDao.insertCard(
                dbPlayingCard = DbPlayingCard(
                    question = question,
                    answer = answer,
                    isLearned = false,
                    isAnswered = false,
                    deckId = deckId,
                ),
            )
        }
    }

    override suspend fun deleteCard(cardId: Int) {
        runBlocking(bgDispatcher) {
            deckDao.deleteCard(id = cardId)
        }
    }

    private val favorites = deckDao.getFavorites().map {
        it.map { dbFavoriteDeck ->
            Deck(
                id = dbFavoriteDeck.id,
                name = dbFavoriteDeck.name,
                size = dbFavoriteDeck.size,
                isFavorite = true,
            )
        }
    }

    override fun favoriteDecks(): Flow<List<Deck>> = favorites

    override fun cards(): Flow<List<PlayingCard>> = cards


    override suspend fun addDeckToFavorites(deckId: Int) {
        runBlocking(bgDispatcher) {
            deckDao.insertFavorite(
                DbFavoriteDeck(
                    id = deckId,
                    name = findDeck(deckId)!!.name,
                    size = getDeckSize(deckId).first(),
                )
            )
        }
    }

    private suspend fun findDeck(deckId: Int): Deck? {
        val allDecks = decks.first()
        allDecks.forEach {
            if (it.id == deckId) {
                return it
            }
        }
        return null
    }

    override suspend fun removeDeckFromFavorites(deckId: Int) {
        runBlocking(bgDispatcher) {
            deckDao.deleteFavorite(deckId)
        }
    }

    override suspend fun toggleFavorites(deckId: Int) {
        runBlocking(bgDispatcher) {
            if (favorites.first().map(Deck::id).contains(deckId)) {
                removeDeckFromFavorites(deckId)
            } else {
                addDeckToFavorites(deckId)
            }
        }
    }

    override fun deckDetails(deckId: Int): Flow<DeckDetails> = flow {
        val deckDetails = DeckDetails(
            deck = findDeck(deckId)!!,
            cards = cards.first().filter { it.deckId == deckId })
        emit(deckDetails)
    }.combine(cards) { deckDetails, cards ->
        DeckDetails(deckDetails.deck, cards.filter { it.deckId == deckId })
    }.shareIn(
        scope = CoroutineScope(bgDispatcher), started = SharingStarted.Eagerly, replay = 1
    )

    private suspend fun getDeckSize(deckId: Int): Flow<Int> = flow {
        emit(cards.first().filter { it.deckId == deckId }.size)
    }

    override suspend fun changeIsAnswered(cardId: Int, isAnswered: Boolean) {
        runBlocking(bgDispatcher) {
            deckDao.updateIsAnswered(isAnswered = isAnswered, id = cardId)
        }
    }

    override suspend fun changeIsLearned(cardId: Int, isLearned: Boolean) {
        runBlocking(bgDispatcher) {
            deckDao.updateIsLearned(isLearned = isLearned, id = cardId)
        }
    }
}
