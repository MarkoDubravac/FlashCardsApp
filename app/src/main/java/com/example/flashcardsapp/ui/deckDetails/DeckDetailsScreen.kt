package com.example.flashcardsapp.ui.deckDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.R
import com.example.flashcardsapp.data.repository.FakeDeckRepository
import com.example.flashcardsapp.ui.component.CardAddField
import com.example.flashcardsapp.ui.component.CardInsertData
import com.example.flashcardsapp.ui.component.DeckNameLabel
import com.example.flashcardsapp.ui.component.PlayCard
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapperImpl
import com.example.flashcardsapp.ui.theme.ButtonBlue
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing
import com.example.flashcardsapp.util.showToast
import kotlinx.coroutines.Dispatchers

@Composable
fun DeckDetailsRoute(
    viewModel: DeckDetailsViewModel, onPlayClick: (Int) -> Unit
) {
    val deckDetailsViewState: DeckDetailsViewState by viewModel.deckDetailsViewState.collectAsState()
    DeckDetailsScreen(
        deckDetailsViewState = deckDetailsViewState,
        onAddClick = { newCard -> viewModel.insertCard(newCard.question, newCard.answer) },
        onDeleteClick = viewModel::deleteCard,
        onShowAnswerClick = { viewModel.changeIsAnswered(it, isAnswered = true) },
        onNegativeAnswer = { viewModel.changeIsAnswered(it, isAnswered = false) },
        onPositiveAnswer = { viewModel.changeIsLearned(it, isLearned = true) },
        onRefreshClick = { viewModel.resetDeck(deckId = deckDetailsViewState.id) },
        onPlayClick = onPlayClick
    )
}


@Composable
fun DeckDetailsScreen(
    deckDetailsViewState: DeckDetailsViewState,
    modifier: Modifier = Modifier,
    onAddClick: (CardInsertData) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onShowAnswerClick: (Int) -> Unit,
    onNegativeAnswer: (Int) -> Unit,
    onPositiveAnswer: (Int) -> Unit,
    onRefreshClick: () -> Unit,
    onPlayClick: (Int) -> Unit
) {
    var textFieldShow by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val context = LocalContext.current
    val enterAnimation = dimensionResource(id = R.dimen.enter_animation)
    val exitAnimation = dimensionResource(id = R.dimen.exit_animation)
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.padding(MaterialTheme.spacing.small)) {
            DeckNameLabel(
                name = "Welcome to " + deckDetailsViewState.name + "!",
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            )
            if (deckDetailsViewState.cards.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_cards_message),
                    style = Typography.h2,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.small)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyRow(
                    contentPadding = PaddingValues(vertical = MaterialTheme.spacing.extraSmall),
                    content = {
                        items(deckDetailsViewState.cards.size) { card ->
                            PlayCard(
                                playCardViewState = deckDetailsViewState.cards[card].playCardViewState,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.small)
                                    .width(dimensionResource(id = R.dimen.details_card_width))
                                    .height(dimensionResource(id = R.dimen.details_card_height)),
                                onDeleteClick = { onDeleteClick(deckDetailsViewState.cards[card].playCardViewState.id) },
                                onShowAnswerClick = { onShowAnswerClick(deckDetailsViewState.cards[card].playCardViewState.id) },
                                onNegativeAnswer = { onNegativeAnswer(deckDetailsViewState.cards[card].playCardViewState.id) },
                                onPositiveAnswer = { onPositiveAnswer(deckDetailsViewState.cards[card].playCardViewState.id) },
                                canDelete = true
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
                Image(
                    painter = painterResource(id = R.drawable.ic_refresh),
                    contentDescription = stringResource(
                        id = R.string.refresh
                    ),
                    modifier = Modifier
                        .clickable { onRefreshClick() }
                        .align(Alignment.CenterHorizontally)
                        .background(ButtonBlue.copy(alpha = 0.6f), shape = CircleShape)
                        .padding(MaterialTheme.spacing.small),
                )
            }
        }
        FloatingActionButton(
            backgroundColor = floatingButtonColor,
            modifier = Modifier
                .padding(
                    end = MaterialTheme.spacing.medium,
                    bottom = dimensionResource(id = R.dimen.bottom_fab_padding)
                )
                .align(alignment = Alignment.BottomEnd),
            onClick = { textFieldShow = true },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(id = R.string.add_description),
            )
        }

        FloatingActionButton(
            backgroundColor = floatingButtonColor,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.medium,
                    bottom = dimensionResource(id = R.dimen.bottom_fab_padding)
                )
                .align(alignment = Alignment.BottomStart),
            onClick = {
                if (deckDetailsViewState.cards.isEmpty()) {
                    showToast("Deck is empty!", context)
                } else onPlayClick(deckDetailsViewState.id)
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = stringResource(id = R.string.play),
            )
        }

        AnimatedVisibility(
            textFieldShow,
            enter = slideInHorizontally { with(density) { enterAnimation.roundToPx() } },
            exit = slideOutHorizontally { with(density) { exitAnimation.roundToPx() } },
            modifier = Modifier.align(Alignment.Center)
        ) {
            CardAddField(modifier = Modifier.align(Alignment.Center), onAddClick = { cardData ->
                val question = cardData.question
                val answer = cardData.answer
                val isDuplicate =
                    deckDetailsViewState.cards.any { it.playCardViewState.question == question }

                when {
                    question.isEmpty() -> showToast("Question field cannot be empty", context)
                    isDuplicate -> showToast(
                        "Card with this question already exists in this deck", context
                    )
                    answer.isEmpty() -> showToast("Answer field cannot be empty", context)
                    else -> {
                        onAddClick(cardData)
                        showToast("New Card Added", context)
                    }
                }
            }, onBackClick = { textFieldShow = false }, openDialog = textFieldShow
            )
        }
    }
}

@Preview
@Composable
fun DeckDetailsScreenPreview() {
    DeckDetailsRoute(viewModel = DeckDetailsViewModel(
        FakeDeckRepository(Dispatchers.IO), DeckDetailsMapperImpl(), deckId = 1
    ), onPlayClick = {})
}
