package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.R
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
    playCardViewState: PlayCardViewState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = { },
    onShowAnswerClick: () -> Unit = { },
    onNegativeAnswer: () -> Unit = { },
    onPositiveAnswer: () -> Unit = { },
    canDelete: Boolean
) {
    var showDialog by remember { mutableStateOf(false) }
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
            if (canDelete) {
                Image(painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = stringResource(id = R.string.delete_icon),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(MaterialTheme.spacing.small)
                        .clickable { showDialog = true })
            }
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
            PlayCardBottomButtons(
                isAnswered = playCardViewState.isAnswered,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onShowAnswerClick = onShowAnswerClick,
                onNegativeAnswer = onNegativeAnswer,
                onPositiveAnswer = onPositiveAnswer
            )

            if (showDialog) {
                DeleteConfirmationDialog(onConfirm = {
                    onDeleteClick()
                    showDialog = false
                }, onDismiss = { showDialog = false }, name = "this card")
            }
        }
    }
}

@Composable
fun PlayCardBottomButtons(
    isAnswered: Boolean,
    modifier: Modifier = Modifier,
    onShowAnswerClick: () -> Unit = { },
    onNegativeAnswer: () -> Unit = { },
    onPositiveAnswer: () -> Unit = { }
) {
    Column(modifier = modifier) {
        if (isAnswered) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small)
            ) {
                Button(
                    onClick = onNegativeAnswer,
                    modifier = Modifier
                        .weight(1f)
                        .padding(MaterialTheme.spacing.small),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = falseRed, contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.false_answer))
                }
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Button(
                    onClick = onPositiveAnswer,
                    modifier = Modifier
                        .weight(1f)
                        .padding(MaterialTheme.spacing.small),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = rightGreen, contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.right_answer))
                }
            }
        } else {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = floatingButtonColor),
                onClick = onShowAnswerClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(MaterialTheme.spacing.small)
            ) {
                Text(text = stringResource(id = R.string.show_answer))
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
    PlayCard(playCardViewState = playCardViewState, canDelete = false)
}
