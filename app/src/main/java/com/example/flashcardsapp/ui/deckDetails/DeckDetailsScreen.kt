package com.example.flashcardsapp.ui.deckDetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.R
import com.example.flashcardsapp.data.repository.FakeDeckRepository
import com.example.flashcardsapp.ui.component.DeckNameLabel
import com.example.flashcardsapp.ui.component.PlayCard
import com.example.flashcardsapp.ui.deckDetails.mapper.DeckDetailsMapperImpl
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.spacing
import kotlinx.coroutines.Dispatchers

@Composable
fun DeckDetailsRoute(
    viewModel: DeckDetailsViewModel
) {
    val deckDetailsViewState: DeckDetailsViewState by viewModel.deckDetailsViewState.collectAsState()
    DeckDetailsScreen(deckDetailsViewState = deckDetailsViewState)
}

@Composable
fun DeckDetailsScreen(
    deckDetailsViewState: DeckDetailsViewState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
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
                    //cards.size se koristi jer jos nema logika za size
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
                Text(text = "Number of cards in deck: ")
                Text(text = deckDetailsViewState.size.toString())
            }
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
