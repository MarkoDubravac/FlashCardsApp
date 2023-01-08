package com.example.flashcardsapp.ui.deckDetails.mapper

import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.model.PlayingCard
import com.example.flashcardsapp.ui.component.PlayCardViewState
import com.example.flashcardsapp.ui.deckDetails.CardViewState
import com.example.flashcardsapp.ui.deckDetails.DeckDetailsViewState

class DeckDetailsMapperImpl : DeckDetailsMapper {
    override fun toDeckDetailsViewState(deckDetails: DeckDetails) = DeckDetailsViewState(
        id = deckDetails.deck.id,
        name = deckDetails.deck.name,
        size = deckDetails.deck.size,
        cards = deckDetails.cards.map { toCardsViewState(it) },
    )

    private fun toCardsViewState(card: PlayingCard) = CardViewState(
        playCardViewState = PlayCardViewState(
            id = card.id,
            question = card.question,
            answer = card.answer,
            isLearned = card.isLearned,
            isAnswered = card.isAnswered,
        )
    )
}
