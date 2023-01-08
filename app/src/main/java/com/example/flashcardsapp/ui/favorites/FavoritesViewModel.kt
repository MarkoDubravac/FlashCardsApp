package com.example.flashcardsapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.ui.favorites.mapper.FavoritesMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val deckRepository: DeckRepository,
    favoritesMapper: FavoritesMapper,
) : ViewModel() {
    private val _favoritesViewState = MutableStateFlow(FavoritesViewState(emptyList()))
    val favoritesViewState: StateFlow<FavoritesViewState> =
        deckRepository.favoriteDecks().map { decks -> favoritesMapper.toFavoritesViewState(decks) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, _favoritesViewState.value)

    fun toggleFavorite(deckId: Int) {
        viewModelScope.launch {
            deckRepository.toggleFavorites(deckId)
        }
    }
}
