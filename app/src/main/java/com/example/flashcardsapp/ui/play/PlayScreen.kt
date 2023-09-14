package com.example.flashcardsapp.ui.play

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.component.PlayCard
import com.example.flashcardsapp.ui.theme.spacing
import com.example.flashcardsapp.util.Notification
import kotlinx.coroutines.delay


@Composable
fun PlayRoute(
    viewModel: PlayViewModel
) {
    val playViewState: PlayViewState by viewModel.playViewState.collectAsState()
    PlayScreen(playViewState = playViewState,
        onShowAnswerClick = { viewModel.changeIsAnswered(isAnswered = true) },
        onPositiveAnswer = { viewModel.changeIsLearned(isLearned = true) },
        onNegativeAnswer = { viewModel.changeIsLearned(isLearned = false) },
        onTimerFinished = { viewModel.timerFinished() })
}

@Composable
fun PlayScreen(
    playViewState: PlayViewState,
    modifier: Modifier = Modifier,
    onShowAnswerClick: () -> Unit,
    onNegativeAnswer: () -> Unit,
    onPositiveAnswer: () -> Unit,
    onTimerFinished: () -> Unit
) {
    var isPaused by remember { mutableStateOf(false) }
    val countdownTime by remember { mutableStateOf(15) }

    Column(modifier = modifier.fillMaxSize()) {
        CountdownTimer(totalTime = countdownTime, onTimerFinished, isPaused = isPaused)
        PlayCard(
            playCardViewState = playViewState.card,
            modifier = Modifier.padding(MaterialTheme.spacing.mediumToLarge),
            onShowAnswerClick = {
                onShowAnswerClick()
                isPaused = true
            },
            onPositiveAnswer = {
                onPositiveAnswer()
                isPaused = false
            },
            onNegativeAnswer = {
                onNegativeAnswer()
                isPaused = false
            },
            onDeleteClick = { },
            canDelete = false
        )
    }
}

@Composable
fun CountdownTimer(
    totalTime: Int, onTimerFinished: () -> Unit, isPaused: Boolean
) {
    var currentTimer by remember { mutableStateOf(totalTime) }
    val progress by animateFloatAsState(
        targetValue = currentTimer.toFloat(), animationSpec = ProgressIndicatorAnimationSpec
    )
    val context = LocalContext.current

    LaunchedEffect(key1 = isPaused, key2 = currentTimer) {
        while (currentTimer > 0 && !isPaused) {
            delay(1000)
            currentTimer -= 1
        }
        if (isPaused) currentTimer = totalTime
    }
    if (currentTimer == 0) {
        val notification = Notification(
            context = context,
            title = stringResource(id = R.string.notification_title),
            message = stringResource(
                id = R.string.notification_message
            )
        )
        notification.sendNotification()
        onTimerFinished()
        currentTimer = totalTime
    }

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.smallToMedium)
            .height(MaterialTheme.spacing.small),
        progress = progress / totalTime,
    )
}

private val ProgressIndicatorAnimationSpec = tween<Float>(
    durationMillis = 1000, easing = LinearEasing
)
