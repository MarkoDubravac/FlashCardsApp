package com.example.flashcardsapp.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.Typography
import com.example.flashcardsapp.ui.theme.deckCardGradiantPrimary
import com.example.flashcardsapp.ui.theme.deckCardGradiantSecondary
import com.example.flashcardsapp.ui.theme.spacing

@Composable
fun InfoRoute() {
    InfoScreen()
}

@Composable
fun InfoScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.info_screen),
            style = Typography.h2,
            modifier = Modifier.padding(MaterialTheme.spacing.small),
        )
        Text(
            text = stringResource(id = R.string.info_screen_description),
            style = Typography.h2,
            modifier = Modifier.padding(MaterialTheme.spacing.small),
        )
        InfoCard(stringResId = stringResource(id = R.string.info_screen_how_to_use1))
        InfoCard(stringResId = stringResource(id = R.string.info_screen_how_to_use2))
        InfoCard(stringResId = stringResource(id = R.string.info_screen_how_to_use3))
        InfoCard(stringResId = stringResource(id = R.string.info_screen_how_to_use4))
        InfoCard(stringResId = stringResource(id = R.string.info_screen_how_to_use5))
    }
}

@Composable
fun InfoCard(stringResId: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp), backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(deckCardGradiantPrimary, deckCardGradiantSecondary)
                ),
            )
        ) {
            Text(
                text = stringResId,
                style = Typography.body1,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(MaterialTheme.spacing.small),
            )
        }
    }
}
