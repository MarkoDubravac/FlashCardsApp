package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.*

data class DeckCardViewState(
    val deckId: Int, val name: String, val size: Int, val isFavorite: Boolean
)

@Composable
fun DeckCard(
    deckCardViewState: DeckCardViewState,
    modifier: Modifier = Modifier,
    onDeckClick: () -> Unit = { },
    onLikeButtonClick: () -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {
    Card(
        modifier = modifier.clickable { onDeckClick() },
        elevation = dimensionResource(id = R.dimen.card_elevation),
        shape = Shapes.large,
        backgroundColor = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            deckCardGradiantPrimary, deckCardGradiantSecondary
                        )
                    ),
                )
        ) {
            NumberInCircle(
                number = deckCardViewState.size,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(MaterialTheme.spacing.extraSmall),
            )
            FavoriteButton(
                isFavorite = deckCardViewState.isFavorite,
                onClick = onLikeButtonClick,
                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall)
            )
            Text(
                text = deckCardViewState.name,
                style = Typography.h1,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(MaterialTheme.spacing.small),
                textAlign = TextAlign.Center,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Image(painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = stringResource(id = R.string.delete_icon),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(MaterialTheme.spacing.small)
                    .clickable { onDeleteClick() })
        }
    }
}

@Composable
fun NumberInCircle(
    number: Int, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(dimensionResource(id = R.dimen.number_in_circle_size))
            .clip(shape = CircleShape)
            .background(color = ButtonBlue.copy(alpha = 0.6f))
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier.align(Alignment.Center),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun DeckCardPreview() {
    val deckCardViewState = DeckCardViewState(
        deckId = 1, name = "Deck1", size = 86, isFavorite = true
    )
    DeckCard(
        deckCardViewState = deckCardViewState,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .width(150.dp)
            .height(240.dp),
        onDeckClick = { },
        onLikeButtonClick = { },
    )
}
