package com.z1.pokedex.core.common.designsystem.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.core.common.designsystem.extensions.normalizedItemPosition
import com.z1.pokedex.core.common.designsystem.theme.CustomRippleTheme
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.feature.home.domain.model.Pokemon
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    pokemon: Pokemon,
    pokemonClickedList: Set<String>,
    isShowGridList: Boolean,
    onPokemonClick: (pokemon: Pokemon) -> Unit
) {
    val dimen =
        if (isShowGridList) PokedexZ1Theme.gridList
        else PokedexZ1Theme.verticalList

    val pokemonAlreadyClicked by remember {
        mutableStateOf(pokemonClickedList.contains(pokemon.name))
    }

    val imageModifier =
        if (isShowGridList) Modifier
        else {
            Modifier
                .graphicsLayer {
                    val value =
                        1 - (listState.layoutInfo.normalizedItemPosition(pokemon.name).absoluteValue * 0.25F)
                    //alpha = value
                    scaleX = value
                    scaleY = value
                }
        }

    var startAnimation by remember { mutableStateOf(false) }
    val scalePokemon by remember { mutableStateOf(Animatable(1.3f)) }
    LaunchedEffect(key1 = startAnimation, key2 = pokemonAlreadyClicked) {
        if (startAnimation && pokemonAlreadyClicked) {
            onPokemonClick(pokemon)
            return@LaunchedEffect
        }
        if (startAnimation) {
            scalePokemon.animateTo(1.7f, animationSpec = tween(durationMillis = 300))
            scalePokemon.animateTo(0f, animationSpec = tween(durationMillis = 500))
            onPokemonClick(pokemon)
        }
    }

    val animationRotation by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f,
        animationSpec = tween(500, easing = LinearEasing) ,
        label = "animation-rotation"
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color(pokemon.dominantColor()), Color(pokemon.vibrantDarkColor())),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(vertical = dimen.normal)
    ) {
        val (image, name, card, canva, textNumber) = createRefs()
        CompositionLocalProvider(
            LocalRippleTheme provides CustomRippleTheme(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dimen.brushShape))
                    .constrainAs(card) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .height(dimen.boxSize)
                    .drawBehind {
                        drawRoundRect(brush = brush)
                    }
                    .clickable { startAnimation = true }
            ) {
                Image(
                    modifier = Modifier
                        .size(dimen.imagePlaceHolderSize)
                        .alpha(0.4f)
                        .padding(dimen.normal)
                        .rotate(animationRotation),
                    painter = painterResource(id = R.drawable.pokeball_placeholder),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = ""
                )
            }
        }

        CustomShineImage(
            modifier = imageModifier
                .size(dimen.canvaSize)
                .constrainAs(canva) {
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                },
        )

        pokemon.image?.asImageBitmap()?.let {
            ImageWithShadow(
                modifier = imageModifier
                    .requiredWidth(dimen.imageSize)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, dimen.imageMarginEnd)
                    }
                    .scale(scalePokemon.value),
                imageBitmap = it,
                contentScale = ContentScale.Fit,
                colorFilter =
                if (pokemonAlreadyClicked) null
                else ColorFilter.tint(Color(0, 0, 0, 255)),
                offsetX = dimen.offsetX,
                offsetY = dimen.offsetY
            )
        }

        Text(
            modifier = Modifier
                .padding(end = dimen.normal)
                .constrainAs(name) {
                    start.linkTo(card.start, dimen.normal)
                    bottom.linkTo(card.bottom, dimen.normal)
                }
                .graphicsLayer {
                    if (pokemonAlreadyClicked.not()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val blur = 20f
                            renderEffect = RenderEffect
                                .createBlurEffect(
                                    blur, blur, Shader.TileMode.DECAL
                                )
                                .asComposeRenderEffect()
                        } else {
                            alpha = 0f
                        }
                    }
                },
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onSurface,
            style =
            if (isShowGridList) MaterialTheme.typography.titleSmall
            else MaterialTheme.typography.titleMedium,
        )

        Text(
            modifier = Modifier
                .constrainAs(textNumber) {
                    top.linkTo(card.top, dimen.normal)
                    start.linkTo(card.start, dimen.normal)
                },
            text = String.format(Locale.getDefault(), "#%03d", pokemon.getIndex()),
            color = MaterialTheme.colorScheme.onSurface,
            style =
            if (isShowGridList) MaterialTheme.typography.titleSmall
            else MaterialTheme.typography.titleMedium,
        )
    }
}