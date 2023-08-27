package com.example.flashcardsapp.ui.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.ui.completed.mapper.CompletedMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CompletedViewModel(
    private val deckRepository: DeckRepository, completedMapper: CompletedMapper
) : ViewModel() {
    private val _completedViewState = MutableStateFlow(CompletedViewState(emptyList()))
    val completedViewState: StateFlow<CompletedViewState> =
        deckRepository.decks().map { decks -> completedMapper.toCompletedViewState(decks) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, _completedViewState.value)

}
