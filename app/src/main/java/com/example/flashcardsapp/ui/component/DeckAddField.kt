package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.floatingButtonColor
import com.example.flashcardsapp.ui.theme.spacing

@Composable
fun deckAddField(
    modifier: Modifier = Modifier,
    onAddClick: (String) -> Unit,
    onBackClick: () -> Unit,
): String {
    var textState by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .background(floatingButtonColor.copy(alpha = 0.9f))
            .padding(MaterialTheme.spacing.medium)
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(text = stringResource(id = R.string.enter_deck_name)) },
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
            Button(onClick = { onAddClick(textState) }) {
                Text(text = stringResource(id = R.string.add))
            }
        }
    }
    return textState
}
