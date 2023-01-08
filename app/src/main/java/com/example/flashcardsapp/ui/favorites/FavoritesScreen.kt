package com.example.flashcardsapp.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.flashcardsapp.ui.component.DeckCard
import com.example.flashcardsapp.ui.favorites.mapper.FavoritesMapperImpl
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.spacing
import kotlinx.coroutines.Dispatchers

@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel, onNavigateToDeckDetails: (Int) -> Unit
) {
    val favoritesViewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()
    FavoritesScreen(
        favoritesViewState = favoritesViewState,
        onNavigateToDeckDetails = onNavigateToDeckDetails,
        onFavoriteButtonClick = { viewModel.toggleFavorite(it) },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    favoritesViewState: FavoritesViewState,
    modifier: Modifier = Modifier,
    onNavigateToDeckDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
) {
    Column(modifier = modifier.padding(MaterialTheme.spacing.small)) {
        Text(
            text = stringResource(id = R.string.favorites_screen),
            style = Typography.h2,
            modifier = Modifier.padding(MaterialTheme.spacing.small)
        )
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmallToSmall),
            cells = GridCells.Fixed(count = 2),
            content = {

                itemsIndexed(favoritesViewState.favoriteDecks) { _, deck ->
                    DeckCard(
                        deckCardViewState = deck.deckCardViewState,
                        onDeckClick = { onNavigateToDeckDetails(deck.id) },
                        onLikeButtonClick = { onFavoriteButtonClick(deck.deckCardViewState.deckId) },
                        modifier = modifier.height(dimensionResource(id = R.dimen.details_card_height)),
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesRoute(
        viewModel = FavoritesViewModel(
            FakeDeckRepository(Dispatchers.IO), FavoritesMapperImpl()
        ),
        onNavigateToDeckDetails = { },
    )
}
