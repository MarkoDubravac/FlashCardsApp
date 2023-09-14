package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing

data class CardInsertData(val question: String, val answer: String)

@Composable
fun CardAddField(
    modifier: Modifier = Modifier,
    onAddClick: (CardInsertData) -> Unit,
    onBackClick: () -> Unit,
    openDialog: Boolean,
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    if (openDialog) {
        AlertDialog(modifier = modifier, onDismissRequest = onBackClick, title = {
            Text(text = stringResource(id = R.string.new_card))
        }, text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    value = question,
                    onValueChange = { question = it },
                    label = { Text(text = stringResource(id = R.string.enter_question)) },
                )
                OutlinedTextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text(text = stringResource(id = R.string.enter_answer)) },
                )
            }
        }, buttons = {
            Row(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = onBackClick, colors = ButtonDefaults.buttonColors(
                        backgroundColor = floatingButtonColor, contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.back))
                }
                Button(
                    onClick = {
                        val cardData = CardInsertData(question, answer)
                        onAddClick(cardData)
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = floatingButtonColor, contentColor = Color.Black
                    )
                ) {
                    Text(text = stringResource(id = R.string.add))
                }
            }
        })
    }
}
