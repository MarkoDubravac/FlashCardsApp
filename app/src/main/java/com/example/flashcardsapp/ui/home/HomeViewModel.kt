package com.example.flashcardsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.ui.home.mapper.HomeScreenMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val deckRepository: DeckRepository,
    homeScreenMapper: HomeScreenMapper,
) : ViewModel() {
    private val _homeViewState = MutableStateFlow(HomeViewState(emptyList()))
    val homeViewState: StateFlow<HomeViewState> =
        deckRepository.decks().map { decks -> homeScreenMapper.toHomeViewState(decks) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, _homeViewState.value)

    fun addNewDeck(name: String) {
        viewModelScope.launch {
            deckRepository.insertDeck(name)
        }
    }

    fun toggleFavorite(deckId: Int) {
        viewModelScope.launch {
            deckRepository.toggleFavorites(deckId)
        }
    }

    fun deleteDeck(deckId: Int) {
        viewModelScope.launch {
            deckRepository.deleteDeck(deckId)
        }
    }
}
