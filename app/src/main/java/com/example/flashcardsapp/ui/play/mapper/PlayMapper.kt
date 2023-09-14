package com.example.flashcardsapp.ui.play.mapper

import com.example.flashcardsapp.model.PlayingCard
import com.example.flashcardsapp.ui.play.PlayViewState

interface PlayMapper {
    fun toPlayViewState(playingCard: PlayingCard): PlayViewState
}
