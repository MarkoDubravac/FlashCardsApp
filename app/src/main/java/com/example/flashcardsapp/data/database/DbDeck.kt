package com.example.flashcardsapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "decks")
data class DbDeck(
    val name: String,
    val size: Int,
    val isFavorite: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
