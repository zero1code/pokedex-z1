package com.z1.pokedex.feature.details.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalDensity
import com.z1.pokedex.feature.home.presentation.model.Pokemon
import com.z1.pokedex.feature.utils.ANIM_DURATION
import com.z1.pokedex.feature.utils.SharedElementData
import com.z1.pokedex.feature.utils.lerp
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    sharedElementParams: SharedElementData,
    isAppearing: Boolean,
    onTransitionFinished: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    val density = LocalDensity.current
    val sharedElementProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val titleProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val bgColorProgress = remember { Animatable(if (isAppearing) 0f else 1f) }
    val listProgress = remember { Animatable(if (isAppearing) 0f else 1f) }

    LaunchedEffect(key1 = isAppearing) {
        launch {
            bgColorProgress.animateTo(
                if (isAppearing) 1f else 0f,
                animationSpec = tween(
                    durationMillis = ANIM_DURATION,
                )
            )
        }
    }

    val surfaceMaterialColor = MaterialTheme.colorScheme.surface
    val surfaceMaterialColorTransparent = surfaceMaterialColor.copy(alpha = 0f)
    val surfaceColor = remember {
        derivedStateOf {
            lerp(
                surfaceMaterialColorTransparent,
                surfaceMaterialColor,
                bgColorProgress.value
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = surfaceColor.value)
            }
    ) {

    }

}