package com.z1.pokedex.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.extensions.thenIf
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme

@Composable
fun CustomLoading(
    modifier: Modifier = Modifier,
    @StringRes loadingMessage: Int = R.string.label_loading_more_pokemon,
    iconSize: Dp,
    animateIcon: Boolean,
    animateVelocity: Int = 1_000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-transition")
    val animationRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(animateVelocity, easing = LinearEasing),
        ),
        label = "animation-rotation"
    )

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
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
                .size(iconSize)
                .thenIf(animateIcon) {
                    rotate(animationRotation)
                },
            painter = painterResource(id = R.drawable.pokeball_placeholder),
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(
                    top = PokedexZ1Theme.dimen.medium,
                    bottom = PokedexZ1Theme.dimen.medium
                )
                .constrainAs(text) {
                    top.linkTo(image.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            text = stringResource(id = loadingMessage),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}