package com.example.flashcardsapp.ui.deckDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.R
import com.example.flashcardsapp.data.repository.FakeDeckRepository
import com.example.flashcardsapp.ui.component.CardInsertData
import com.example.flashcardsapp.ui.component.DeckNameLabel
import com.example.flashcardsapp.ui.component.PlayCard
import com.example.flashcardsapp.ui.component.cardAddField
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapperImpl
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing
import kotlinx.coroutines.Dispatchers

@Composable
fun DeckDetailsRoute(
    viewModel: DeckDetailsViewModel
) {
    val deckDetailsViewState: DeckDetailsViewState by viewModel.deckDetailsViewState.collectAsState()
    DeckDetailsScreen(deckDetailsViewState = deckDetailsViewState, onAddClick = {
        viewModel.insertCard(cardInsertData.question, cardInsertData.answer)
    })
}

var cardInsertData = CardInsertData(question = "", answer = "")

@Composable
fun DeckDetailsScreen(
    deckDetailsViewState: DeckDetailsViewState,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    var textFieldShow by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxSize()) {
        Column {
            DeckNameLabel(
                name = deckDetailsViewState.name,
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            )
            if (deckDetailsViewState.cards.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_cards_message),
                    style = Typography.h2,
                    modifier = Modifier.padding(MaterialTheme.spacing.small)
                )
            } else {
                LazyRow(
                    contentPadding = PaddingValues(vertical = MaterialTheme.spacing.small),
                    content = {
                        items(deckDetailsViewState.cards.size) { card ->
                            PlayCard(
                                playCardViewState = deckDetailsViewState.cards[card].playCardViewState,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.small)
                                    .width(dimensionResource(id = R.dimen.details_card_width))
                                    .height(dimensionResource(id = R.dimen.details_card_height))
                            )
                        }
                    },
                )
                Row(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
                    Text(text = stringResource(id = R.string.number_of_cards))
                    Text(text = deckDetailsViewState.size.toString())
                }
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

        if (textFieldShow) {
            cardInsertData = cardAddField(
                modifier = Modifier.align(Alignment.Center),
                onAddClick = onAddClick,
                onBackClick = { textFieldShow = false },
            )
        }
    }
}

@Preview
@Composable
fun DeckDetailsScreenPreview() {
    DeckDetailsRoute(
        viewModel = DeckDetailsViewModel(
            FakeDeckRepository(Dispatchers.IO), DeckDetailsMapperImpl(), deckId = 1
        )
    )
}
