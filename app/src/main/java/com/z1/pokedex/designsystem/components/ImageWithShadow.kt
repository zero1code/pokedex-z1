package com.z1.pokedex.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme

@Composable
fun ImageWithShadow(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap,
    contentScale: ContentScale
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = modifier
                .offset(5.dp, 5.dp),
            bitmap = imageBitmap,
            contentScale = contentScale,
            contentDescription = "back",
            colorFilter = ColorFilter.tint(Color(0, 0, 0, 40))
        )

        Image(
            modifier = modifier,
            bitmap = imageBitmap,
            contentScale = contentScale,
            contentDescription = "front"
        )
    }
}

@Preview
@Composable
private fun ImageWithShadowPreview() {
    PokedexZ1Theme {
//        ImageWithShadow()
    }
}