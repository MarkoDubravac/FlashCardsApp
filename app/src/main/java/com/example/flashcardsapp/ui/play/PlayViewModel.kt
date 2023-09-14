package com.example.flashcardsapp.ui.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardsapp.data.repository.DeckRepository
import com.example.flashcardsapp.model.PlayingCard
import com.example.flashcardsapp.ui.component.PlayCardViewState
import com.example.flashcardsapp.ui.play.mapper.PlayMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class PlayViewModel(
    private val deckRepository: DeckRepository, private val playMapper: PlayMapper, deckId: Int
) : ViewModel() {
    private val _deckId = deckId
    private val _playViewState: MutableStateFlow<PlayViewState> = MutableStateFlow(
        PlayViewState(
            deckId = 0, card = PlayCardViewState(
                id = 0, question = "", answer = "", isAnswered = false, isLearned = false
            )
        )
    )

    val playViewState: StateFlow<PlayViewState> = _playViewState

    init {
        initializePlayViewState()
    }

    private fun initializePlayViewState() {
        viewModelScope.launch {
            val randomCard = getRandomCard()
            _playViewState.value = playMapper.toPlayViewState(randomCard!!)
        }
    }

    private suspend fun getRandomCard(): PlayingCard? {
        return withContext(Dispatchers.IO) {
            val cardsInDeck = deckRepository.cards().first().filter {
                it.deckId == _deckId
            }

            val learnedCards = cardsInDeck.filter { it.isLearned }
            val unlearnedCards = cardsInDeck.filterNot { it.isLearned }
            val selectedCard = if (learnedCards.isNotEmpty() && Random.nextFloat() < 0.2f) {
                // Select a learned card with 20% chance
                selectRandomCard(learnedCards)
            } else if (unlearnedCards.isNotEmpty()) {
                // Select an unlearned card with 80% chance
                selectRandomCard(unlearnedCards)
            } else {
                selectRandomCard(cardsInDeck)
            }

            selectedCard
        }
    }

    private fun selectRandomCard(cards: List<PlayingCard>?): PlayingCard? {
        if (cards.isNullOrEmpty()) {
            return null
        }

        val randomIndex = Random.nextInt(cards.size)

        return cards[randomIndex]
    }


    fun changeIsAnswered(isAnswered: Boolean) {
        viewModelScope.launch {
            val cardId = playViewState.value.card.id
            deckRepository.changeIsAnswered(cardId, isAnswered = isAnswered)
            updateIsAnswered(isAnswered)
        }
    }

    fun changeIsLearned(isLearned: Boolean) {
        viewModelScope.launch {
            val cardId = playViewState.value.card.id
            deckRepository.changeIsLearned(cardId, isLearned = isLearned)
            deckRepository.changeIsAnswered(cardId, isAnswered = false)
            updateIsLearned(isLearned)
            initializePlayViewState()
        }
    }

    private fun updateIsAnswered(isAnswered: Boolean) {
        _playViewState.value = _playViewState.value.copy(
            card = _playViewState.value.card.copy(isAnswered = isAnswered)
        )
    }

    private fun updateIsLearned(isLearned: Boolean) {
        _playViewState.value = _playViewState.value.copy(
            card = _playViewState.value.card.copy(isLearned = isLearned)
        )
        _playViewState.value = _playViewState.value.copy(
            card = _playViewState.value.card.copy(isAnswered = false)
        )
    }

    fun timerFinished() {
        initializePlayViewState()
    }
}
