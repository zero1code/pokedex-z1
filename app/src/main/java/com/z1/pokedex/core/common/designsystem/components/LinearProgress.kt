package com.z1.pokedex.core.common.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z1.pokedex.R
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme

@Composable
fun CustomLinearProgress(
    modifier: Modifier = Modifier,
    progressColor: Color,
    @StringRes statisticsLabel: Int,
    currentProgress: Float,
    maxProgress: Int
) {
    var progress by remember { mutableFloatStateOf(0F) }
    val animateProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, 500, FastOutSlowInEasing),
        label = "animation-progress"
    )

    LaunchedEffect(key1 = Unit) {
        progress = (currentProgress / maxProgress.toFloat())
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .weight(0.2f),
            text = stringResource(id = statisticsLabel),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
        )

        LinearProgressIndicator(
            modifier = Modifier
                .weight(1f)
                .height(10.dp),
            progress = { animateProgress },
            strokeCap = StrokeCap.Round,
            trackColor = progressColor.copy(alpha = 0.2f),
            color = progressColor
        )

        Text(
            modifier = Modifier
                .weight(0.3f),
            textAlign = TextAlign.End,
            text = "${currentProgress.toInt()} / $maxProgress",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun CustomLinearProgressPreview() {
    PokedexZ1Theme {
        Column {
            CustomLinearProgress(
                progressColor = Color.Green,
                statisticsLabel = R.string.label_hp,
                currentProgress = 0.5f,
                maxProgress = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomLinearProgress(
                progressColor = Color.Blue,
                statisticsLabel = R.string.label_atk,
                currentProgress = 0.5f,
                maxProgress = 1
            )
        }
    }
}