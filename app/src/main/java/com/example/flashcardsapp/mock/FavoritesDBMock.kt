package com.example.flashcardsapp.mock

import kotlinx.coroutines.flow.MutableStateFlow

object FavoritesDBMock {
    val favoriteIds = MutableStateFlow(setOf<Int>())

    fun insert(deckId: Int) {
        favoriteIds.value += (deckId)
    }

    fun delete(deckId: Int) {
        favoriteIds.value -= (deckId)
    }
}
