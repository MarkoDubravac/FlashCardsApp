package com.example.flashcardsapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Query("SELECT * FROM decks")
    fun getDecks(): Flow<List<DbDeck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(dbDeck: DbDeck)

    @Query("DELETE FROM decks WHERE id = :id")
    fun deleteDeck(id: Int)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<DbFavoriteDeck>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(dbFavoriteDeck: DbFavoriteDeck)

    @Query("DELETE FROM favorites WHERE id = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM cards")
    fun getCards(): Flow<List<DbPlayingCard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(dbPlayingCard: DbPlayingCard)

    @Query("DELETE FROM cards WHERE id = :id")
    fun deleteCard(id: Int)

    @Query("UPDATE cards SET isAnswered = :isAnswered where id = :id")
    fun updateIsAnswered(isAnswered: Boolean, id: Int)

    @Query("UPDATE cards SET isLearned = :isLearned where id = :id")
    fun updateIsLearned(isLearned: Boolean, id: Int)

    @Query("UPDATE cards SET isLearned = 'FALSE', isAnswered = 'FALSE' WHERE deckId = :id")
    fun resetDeck(id: Int)
}
