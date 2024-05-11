package com.z1.pokedex.designsystem.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp

@Composable
fun CustomShineImage(
    modifier: Modifier = Modifier,
    size: Dp,
    scale: Float = -1f,
    colors: List<Color> = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
) {
    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

    Canvas(modifier = modifier
        .size(size)
        .graphicsLayer {
            if (scale != -1f) {
                scaleX = scale
                scaleY = scale
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val blur = 150f
                renderEffect = RenderEffect
                    .createBlurEffect(
                        blur, blur, Shader.TileMode.DECAL
                    )
                    .asComposeRenderEffect()
            }
        }
    ) {
        drawCircle(brush)
    }
}