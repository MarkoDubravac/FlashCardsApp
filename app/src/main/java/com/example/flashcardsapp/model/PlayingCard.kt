package com.example.flashcardsapp.model

data class PlayingCard(
    val id: Int,
    val question: String,
    val answer: String,
    val isLearned: Boolean,
    val isAnswered: Boolean,
    val deckId: Int
)
