package com.z1.pokedex.core.common.designsystem.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    onIconButtonClick: () -> Unit,
    iconImageVector: ImageVector,
    iconTint: Color,
    iconContentDescription: String? = null
) {
    IconButton(
        modifier = modifier,
        onClick = onIconButtonClick
    ) {
        Icon(
            imageVector = iconImageVector,
            tint = iconTint,
            contentDescription = iconContentDescription
        )
    }
}