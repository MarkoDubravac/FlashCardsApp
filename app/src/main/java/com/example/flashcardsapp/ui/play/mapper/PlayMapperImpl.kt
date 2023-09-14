package com.example.flashcardsapp.ui.play.mapper

import com.example.flashcardsapp.model.PlayingCard
import com.example.flashcardsapp.ui.component.PlayCardViewState
import com.example.flashcardsapp.ui.play.PlayViewState

class PlayMapperImpl : PlayMapper {
    override fun toPlayViewState(playingCard: PlayingCard) =
        PlayViewState(deckId = playingCard.deckId, card = PlayCardViewState(
            id = playingCard.id,
            isLearned = playingCard.isLearned,
            isAnswered = playingCard.isAnswered,
            question = playingCard.question,
            answer = playingCard.answer
        ))
}
