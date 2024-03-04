package com.z1.pokedex.designsystem.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

enum class GridOrientation {
    VERTICAL,
    HORIZONTAL
}

@Composable
fun <T> CustomGridList(
    modifier: Modifier = Modifier,
    gridSize: GridCells = GridCells.Fixed(2),
    gridOrientation: GridOrientation = GridOrientation.VERTICAL,
    data: List<T>,
    listState: LazyGridState,
    itemContent: @Composable (Int, T) -> Unit,
    loadingContent: @Composable () -> Unit,
    isLoadingPage: Boolean
) {
    when (gridOrientation) {
        GridOrientation.VERTICAL -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = gridSize,
                state = listState
            ) {
                itemsIndexed(
                    items = data,
                    key = { index, _ -> index }
                ) { index, item ->
                    itemContent(index, item)
                }

                if (isLoadingPage) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        loadingContent()
                    }
                }
            }
        }

        GridOrientation.HORIZONTAL -> {
            LazyHorizontalGrid(
                modifier = modifier,
                rows = gridSize,
                state = listState
            ) {
                itemsIndexed(
                    items = data,
                    key = { index, _ -> index }
                ) { index, item ->
                    itemContent(index, item)
                }

                if (isLoadingPage) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        loadingContent()
                    }
                }
            }
        }
    }
}