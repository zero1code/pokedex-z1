@file:OptIn(ExperimentalFoundationApi::class)

package com.z1.pokedex.designsystem.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class ListType {
    VERTICAL,
    GRID_VERTICAL,
}

@Composable
fun <T> CustomLazyList(
    modifier: Modifier = Modifier,
    gridSize: GridCells = GridCells.Fixed(2),
    listType: ListType = ListType.VERTICAL,
    data: List<T>,
    key: (Int, T) -> Any,
    listState: LazyListState,
    gridListState: LazyGridState,
    itemContent: @Composable (Int, T) -> Unit,
    contentPadding: PaddingValues,
    loadingContent: @Composable () -> Unit,
    headerContent: @Composable () -> Unit,
    footerContent: @Composable () -> Unit,
    isLoadingPage: Boolean,
    isLastPage: Boolean
) {
    when (listType) {
        ListType.VERTICAL -> {
            LazyColumn(
                modifier = modifier,
                horizontalAlignment = Alignment.End,
                state = listState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                contentPadding = contentPadding
            ) {
                item {
                    headerContent()
                }

                itemsIndexed(
                    items = data,
                    key = key
                ) { index, item ->
                    itemContent(index, item)
                }

                if (isLoadingPage && isLastPage.not()) {
                    item {
                        loadingContent()
                    }
                }

                if (isLastPage) {
                    item {
                        footerContent()
                    }
                }
            }
        }

        ListType.GRID_VERTICAL -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = gridSize,
                state = gridListState,
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(contentPadding.calculateTopPadding())
            ) {
                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    headerContent()
                }

                itemsIndexed(
                    items = data,
                    key = key
                ) { index, item ->
                    itemContent(index, item)
                }

                if (isLoadingPage && isLastPage.not()) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        loadingContent()
                    }
                }

                if (isLastPage) {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        footerContent()
                    }
                }
            }
        }
    }
}