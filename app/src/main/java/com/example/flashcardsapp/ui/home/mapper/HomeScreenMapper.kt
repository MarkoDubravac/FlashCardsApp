package com.example.flashcardsapp.ui.home.mapper

import com.example.flashcardsapp.model.Deck
import com.example.flashcardsapp.ui.home.HomeViewState

interface HomeScreenMapper {
    fun toHomeViewState(decks: List<Deck>): HomeViewState
}
