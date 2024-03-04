package com.z1.pokedex.feature.home.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.z1.pokedex.designsystem.components.CustomGridList
import com.z1.pokedex.designsystem.components.CustomLoadingScreen
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
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItems - 10)
        }
    }

    LaunchedEffect(key1 = isLastItemVisible) {
        if (isLastItemVisible) {
            onEvent(Event.LoadNextPage)
        }
    }

    AnimatedVisibility(
        visible = uiState.isFirstLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CustomLoadingScreen()
    }

    AnimatedVisibility(
        visible = uiState.isFirstLoading.not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CustomGridList(
            modifier = modifier.fillMaxSize(),
            data = uiState.pokemonPage.pokemonList,
            listState = listState,
            itemContent = { index, item ->
                Column(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    Text(text = "${index + 1} - ${item.name}")
                }
            },
            isLoadingPage = uiState.isLoadingPage,
            loadingContent = {
                CircularProgressIndicator(
                    modifier = Modifier.wrapContentSize()
                )
            }
        )
    }
}