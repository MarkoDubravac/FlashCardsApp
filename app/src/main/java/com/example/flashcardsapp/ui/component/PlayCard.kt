package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.ui.theme.*

data class PlayCardViewState(
    val id: Int,
    val question: String,
    val answer: String,
    val isLearned: Boolean,
    val isAnswered: Boolean
)

@Composable
fun PlayCard(
    playCardViewState: PlayCardViewState, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(), backgroundColor = Color.Transparent, shape = Shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(deckCardGradiantPrimary, deckCardGradiantSecondary)
                    ),
                )
        ) {
            Text(
                text = playCardViewState.question,
                textAlign = TextAlign.Center,
                style = Typography.h2,
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .weight(weight = 1f)
                    .align(Alignment.CenterHorizontally),
            )
            if (playCardViewState.isAnswered) {
                Text(
                    text = playCardViewState.answer,
                    textAlign = TextAlign.Center,
                    style = Typography.h2,
                    color = Color.White,
                    modifier = Modifier
                        .padding(MaterialTheme.spacing.medium)
                        .weight(weight = 1f)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}

@Preview
@Composable
fun PlayCardPreview() {
    val playCardViewState = PlayCardViewState(
        id = 1,
        question = "How much does the Earth cost?",
        answer = "I miss the old",
        isLearned = true,
        isAnswered = true
    )
    PlayCard(playCardViewState = playCardViewState)
}
