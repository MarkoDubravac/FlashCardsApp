package com.example.flashcardsapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class DbPlayingCard(
    val deckId: Int,
    val question: String,
    val answer: String,
    val isLearned: Boolean,
    val isAnswered: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
