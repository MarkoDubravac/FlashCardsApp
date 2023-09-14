@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.flashcardsapp.ui.deckDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapper
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeckDetailsViewModel(
    private val deckRepository: DeckRepository,
    deckDetailsMapper: DeckDetailsMapper,
    deckId: Int,
) : ViewModel() {
    private val _deckId = deckId

    val deckDetailsViewState: StateFlow<DeckDetailsViewState> = deckRepository.deckDetails(deckId)
        .map { decks -> deckDetailsMapper.toDeckDetailsViewState(decks) }.stateIn(
            viewModelScope, SharingStarted.Eagerly, DeckDetailsViewState(
                id = 0, name = "", size = 0, cards = listOf()
            )
        )

    fun insertCard(question: String, answer: String) {
        viewModelScope.launch {
            deckRepository.insertCard(question, answer, _deckId)
        }
    }

    fun deleteCard(cardId: Int) {
        viewModelScope.launch {
            deckRepository.deleteCard(cardId)
        }
    }

    fun changeIsAnswered(cardId: Int, isAnswered: Boolean) {
        viewModelScope.launch {
            deckRepository.changeIsAnswered(cardId, isAnswered = isAnswered)
        }
    }

    fun changeIsLearned(cardId: Int, isLearned: Boolean) {
        viewModelScope.launch {
            deckRepository.changeIsLearned(cardId, isLearned = isLearned)
        }
    }

    fun resetDeck(deckId: Int) {
        viewModelScope.launch {
            deckRepository.resetDeck(deckId)
        }
    }
}
