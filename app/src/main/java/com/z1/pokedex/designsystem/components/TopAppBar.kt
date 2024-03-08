@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.z1.pokedex.designsystem.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Crop54
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewDay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.designsystem.theme.RedColor

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    isShowGridList: Boolean,
    onActionClick: () -> Unit,
) {

    Column(modifier = modifier.statusBarsPadding()) {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)
            ),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.pokeball_placeholder),
                        colorFilter = ColorFilter.tint(RedColor),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            },
            actions = {
                CustomIconButton(
                    onIconButtonClick = onActionClick,
                    iconImageVector =
                    if (isShowGridList.not()) Icons.Outlined.GridView
                    else Icons.Outlined.ViewDay,
                    iconTint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun PreviewDestinationBar() {
    PokedexZ1Theme {
        CustomTopAppBar(
            onActionClick = {},
            isShowGridList = true
        )
    }
}
