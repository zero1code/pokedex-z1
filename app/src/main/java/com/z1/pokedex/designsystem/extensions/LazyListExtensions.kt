package com.z1.pokedex.designsystem.extensions

import androidx.compose.foundation.lazy.LazyListLayoutInfo

fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull {it.key == key }
        ?.let {
            val center = (viewportEndOffset + viewportStartOffset - it.size) / 2F
            (it.offset.toFloat() - center) / center
        } ?: 0F