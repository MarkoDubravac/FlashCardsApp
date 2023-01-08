package com.example.flashcardsapp.navigation

const val DECK_DETAILS_ROUTE = "DeckDetails"
const val DECK_ID_KEY = "deckId"
const val DECK_DETAILS_ROUTE_WITH_PARAMS = "$DECK_DETAILS_ROUTE/{$DECK_ID_KEY}"

object DeckDetailsDestination : FscAppDestination(DECK_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigationRoute(deckId: Int): String = "$DECK_DETAILS_ROUTE/$deckId"
}
