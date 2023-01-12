package com.example.flashcardsapp.data.repository

import com.example.flashcardsapp.data.database.DbDeck
import com.example.flashcardsapp.data.database.DbFavoriteDeck
import com.example.flashcardsapp.data.database.DbPlayingCard
import com.example.flashcardsapp.data.database.DeckDao
import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.model.PlayingCard
import kotlinx.coroutines.CoroutineDispatcher
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
                size = dbDeck.size,
                isFavorite = dbDeck.isFavorite
            )
        }
    }

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
    }

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
                    size = findDeck(deckId)!!.size,
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

    /*private suspend fun getCardsForDeck(deckId: Int): List<PlayingCard> {
        val newList = mutableListOf<PlayingCard>()
        cards.map { cards ->
            cards.forEach {
                if (it.deckId == deckId) {
                    newList.add(it)
                }
            }
        }.collect(newList)
        return newList
    }*/

    override fun deckDetails(deckId: Int): Flow<DeckDetails> = flow {
        emit(DeckDetails(deck = findDeck(deckId)!!, cards = cards.first().filter { it.deckId == deckId }))
    }.flowOn(bgDispatcher)

}
