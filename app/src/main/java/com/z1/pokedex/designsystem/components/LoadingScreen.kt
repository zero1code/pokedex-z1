package com.z1.pokedex.designsystem.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme

@Composable
fun CustomLoadingScreen(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val animationRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
        ),
        label = "animationRotation"
    )
    ConstraintLayout(
        modifier = modifier.size(350.dp),
    ) {
        val (image, text) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .size(100.dp)
                .rotate(animationRotation),
            painter = painterResource(id = R.drawable.pokeball_placeholder),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(image.bottom, 16.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            text = "Carregando pokemons...",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview
@Composable
fun PreviewCustomLoadingScreen() {
    PokedexZ1Theme {
        CustomLoadingScreen()
    }
}