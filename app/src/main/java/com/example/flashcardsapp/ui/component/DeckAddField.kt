package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing

@Composable
fun DeckAddField(
    modifier: Modifier = Modifier,
    onAddClick: (String) -> Unit,
    onBackClick: () -> Unit,
    openDialog: Boolean,
) {
    var textState by rememberSaveable { mutableStateOf("") }

    if (openDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onBackClick,
            text = {
                OutlinedTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    label = { Text(text = stringResource(id = R.string.enter_deck_name)) },
                )
            },

            title = {
                Text(text = stringResource(id = R.string.new_deck))
            },
            buttons = {
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
                            onAddClick(textState)
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = floatingButtonColor, contentColor = Color.Black
                        )
                    ) {
                        Text(text = stringResource(id = R.string.add))
                    }
                }
            },
        )
    }
}
