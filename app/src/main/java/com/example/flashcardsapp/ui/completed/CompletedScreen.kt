package com.example.flashcardsapp.ui.completed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.component.DeckCard
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.spacing

@Composable
fun CompletedRoute(viewModel: CompletedViewModel) {
    val completedViewState: CompletedViewState by viewModel.completedViewState.collectAsState()
    CompletedScreen(completedViewState = completedViewState)
}

@Composable
fun CompletedScreen(
    completedViewState: CompletedViewState, modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(MaterialTheme.spacing.small)) {
        Text(
            text = stringResource(id = R.string.completed_screen),
            style = Typography.h2,
            modifier = Modifier.padding(MaterialTheme.spacing.small),
        )
        Text(
            text = stringResource(id = R.string.completed_screen_description),
            style = Typography.h2,
            modifier = Modifier.padding(MaterialTheme.spacing.small),
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmallToSmall),
            content = {
                itemsIndexed(completedViewState.completedDecks) { _, deck ->
                    DeckCard(
                        deckCardViewState = deck.deckCardViewState,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(dimensionResource(id = R.dimen.complete_card_width)),
                    )
                }
            },
        )
    }
}
