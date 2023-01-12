package com.example.flashcardsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DbDeck::class, DbFavoriteDeck::class, DbPlayingCard::class], version = 8
)
abstract class DecksAppDatabase : RoomDatabase() {
    abstract fun deckDao(): DeckDao
}
