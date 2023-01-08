package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.ButtonBlue
import com.example.flashcardsapp.ui.theme.spacing

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = if (isFavorite) R.drawable.ic_liked else R.drawable.ic_not_liked),
        contentDescription = stringResource(id = R.string.favorite_description),
        modifier = modifier
            .clickable {
                onClick()
            }
            .background(
                color = ButtonBlue.copy(alpha = 0.6f),
                shape = CircleShape,
            )
            .padding(MaterialTheme.spacing.small),
    )
}

@Preview
@Composable
fun FavoriteButtonPreview() {
    FavoriteButton(
        modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
        isFavorite = false,
        onClick = { },
    )
}
