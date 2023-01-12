package com.example.flashcardsapp.ui.deckDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.mock.MockDecks
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DeckDetailsViewModel(
    private val deckRepository: DeckRepository,
    deckDetailsMapper: DeckDetailsMapper,
    deckId: Int,
) : ViewModel() {
    private val _deckDetailsViewState = MutableStateFlow(
        deckDetailsMapper.toDeckDetailsViewState(
            MockDecks.getDeckDetails(deckId)
        )
    )
    private val _deckId = deckId

    val deckDetailsViewState: StateFlow<DeckDetailsViewState> = deckRepository.deckDetails(deckId)
        .map { decks -> deckDetailsMapper.toDeckDetailsViewState(decks!!) }.stateIn(
            viewModelScope, SharingStarted.Eagerly, _deckDetailsViewState.value
        )

    fun insertCard(question: String, answer: String) {
        viewModelScope.launch {
            deckRepository.insertCard(question, answer, _deckId)
        }
    }
}
