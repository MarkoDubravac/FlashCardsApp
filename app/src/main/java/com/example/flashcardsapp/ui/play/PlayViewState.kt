package com.example.flashcardsapp.ui.play

import com.example.flashcardsapp.ui.component.PlayCardViewState

data class PlayViewState(
    val deckId: Int,
    val card: PlayCardViewState
)
