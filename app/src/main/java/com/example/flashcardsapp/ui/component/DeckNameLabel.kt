package com.example.flashcardsapp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardsapp.R
import com.example.flashcardsapp.ui.theme.Typography

@Composable
fun DeckNameLabel(
    name: String, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(IntrinsicSize.Max)
    ) {
        Text(
            text = name, maxLines = 2, overflow = TextOverflow.Ellipsis, style = Typography.h2
        )
        Divider(
            color = Color.Black,
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun DeckNameLabelPreview() {
    DeckNameLabel(name = "Razvoj programske podrske objektno orjentiranim nacelima")
}
