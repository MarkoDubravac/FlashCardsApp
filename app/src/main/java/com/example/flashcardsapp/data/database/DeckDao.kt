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


}
