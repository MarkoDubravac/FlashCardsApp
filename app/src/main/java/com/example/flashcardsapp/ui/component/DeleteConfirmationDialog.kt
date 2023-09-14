package com.example.flashcardsapp.ui.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.flashcardsapp.R

@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit, onDismiss: () -> Unit, name: String
) {
    AlertDialog(onDismissRequest = { onDismiss() }, title = {
        Text(text = stringResource(id = R.string.on_delete_item))
    }, text = {
        Text(text = "Are you sure you want to delete $name?")
    }, confirmButton = {
        Button(onClick = {
            onConfirm()
        }) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }, dismissButton = {
        Button(onClick = {
            onDismiss()
        }) {
            Text(text = stringResource(id = R.string.cancel))
        }
    })
}
