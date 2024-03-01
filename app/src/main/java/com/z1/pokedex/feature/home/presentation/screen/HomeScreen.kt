package com.z1.pokedex.feature.home.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.Event
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.UiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEvent: (Event) -> Unit,
) {
    val listState = rememberLazyGridState()

    val isLastItemVisible by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount -1
        }
    }

    LaunchedEffect(key1 = isLastItemVisible) {
        if (isLastItemVisible) {
            onEvent(Event.LoadNextPage)
        }
    }

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        state = listState
    ) {
        itemsIndexed(
            items = uiState.pokemonPage.pokemonList,
            key = { index, item -> "$index ${item.name}" }
        ) { index, item ->

            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "${index + 1} - ${item.name}")
            }

        }
    }
}