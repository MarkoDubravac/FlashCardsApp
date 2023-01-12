package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing

data class CardInsertData(val question: String, val answer: String)

@Composable
fun cardAddField(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onBackClick: () -> Unit,
): CardInsertData {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .background(floatingButtonColor.copy(alpha = 0.9f))
            .padding(MaterialTheme.spacing.medium)
    ) {
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text(text = stringResource(id = R.string.enter_question)) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text(text = stringResource(id = R.string.enter_answer)) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = onBackClick) {
                Text(text = stringResource(id = R.string.back))
            }
            Button(onClick = onAddClick) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
    return CardInsertData(question, answer)
}
