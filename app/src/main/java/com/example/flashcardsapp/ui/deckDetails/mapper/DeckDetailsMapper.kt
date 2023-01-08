package com.example.flashcardsapp.ui.deckDetails.mapper

import com.example.flashcardsapp.model.DeckDetails
import com.example.flashcardsapp.ui.deckDetails.DeckDetailsViewState

interface DeckDetailsMapper {
    fun toDeckDetailsViewState(deckDetails: DeckDetails): DeckDetailsViewState

}
