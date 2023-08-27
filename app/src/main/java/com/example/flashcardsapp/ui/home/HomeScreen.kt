@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.flashcardsapp.ui.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.flashcardsapp.ui.component.DeckCard
import com.example.flashcardsapp.ui.component.deckAddField
import com.example.flashcardsapp.ui.home.mapper.HomeScreenMapperImpl
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers

var textState: String = ""

@Composable
fun HomeRoute(
    viewModel: HomeViewModel, onNavigateToDeckDetails: (Int) -> Unit
) {
    val homeViewState: HomeViewState by viewModel.homeViewState.collectAsState()
    HomeScreen(
        homeViewState = homeViewState,
        onNavigateToDeckDetails = onNavigateToDeckDetails,
        onFavoriteButtonClick = viewModel::toggleFavorite,
        onAddClick = { newName -> viewModel.addNewDeck(newName) },
        onDeleteClick = viewModel::deleteDeck,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    homeViewState: HomeViewState,
    modifier: Modifier = Modifier,
    onNavigateToDeckDetails: (Int) -> Unit,
    onFavoriteButtonClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onAddClick: (String) -> Unit
) {
    var textFieldShow by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val enterAnimation = dimensionResource(id = R.dimen.enter_animation)
    val exitAnimation = dimensionResource(id = R.dimen.exit_animation)
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            Text(
                text = "Hello " + (removeDomainFromEmail(firebaseAuth.currentUser?.email) ?: firebaseAuth.tenantId) + "!",
                style = Typography.h2,
                modifier = Modifier.padding(MaterialTheme.spacing.small)
            )
            if (homeViewState.homeDecks.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_decks_message),
                    style = Typography.h2,
                    modifier = Modifier.padding(MaterialTheme.spacing.small)
                )
            } else {
                LazyVerticalGrid(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmallToSmall),
                    cells = GridCells.Fixed(count = 2),
                    content = {
                        itemsIndexed(homeViewState.homeDecks) { _, deck ->
                            DeckCard(
                                deckCardViewState = deck.deckCardViewState,
                                onDeckClick = { onNavigateToDeckDetails(deck.id) },
                                onLikeButtonClick = { onFavoriteButtonClick(deck.deckCardViewState.deckId) },
                                onDeleteClick = { onDeleteClick(deck.deckCardViewState.deckId) },
                                modifier = modifier.height(dimensionResource(id = R.dimen.deck_card_height)),
                            )
                        }
                    },
                )
            }
        }
        FloatingActionButton(
            backgroundColor = floatingButtonColor,
            modifier = Modifier
                .padding(MaterialTheme.spacing.medium)
                .align(alignment = Alignment.BottomEnd),
            onClick = { textFieldShow = true },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(id = R.string.add_description),
            )
        }

        AnimatedVisibility(
            textFieldShow,
            enter = slideInHorizontally { with(density) { enterAnimation.roundToPx() } },
            exit = slideOutHorizontally { with(density) { exitAnimation.roundToPx() } },
            modifier = Modifier.align(Alignment.Center)
        ) {
            val newName = deckAddField(
                modifier = Modifier.align(Alignment.Center),
                onAddClick = {
                    if (textState.isNotBlank()) {
                        val isDuplicate =
                            homeViewState.homeDecks.any { it.deckCardViewState.name == textState }
                        if (isDuplicate) {
                            Toast.makeText(
                                context, "Deck with this name already exists", Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            onAddClick(textState)
                        }
                    } else {
                        Toast.makeText(
                            context, "Field cannot be empty", Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                onBackClick = { textFieldShow = false },
            )
            textState = newName
        }
    }
}

fun removeDomainFromEmail(email: String?): String? {
    val atIndex = email?.indexOf('@')
    if (email != null) {
         if (atIndex != -1) {
            if (atIndex != null) {
                return email.substring(0, atIndex)
            }
        } else {
            return email
        }
    }
    return null
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeRoute(viewModel = HomeViewModel(
        FakeDeckRepository(Dispatchers.IO), HomeScreenMapperImpl()
    ), onNavigateToDeckDetails = { })
}
