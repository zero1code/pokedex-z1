package com.z1.pokedex.feature.favorites.presentation.screen.viewmodel

import androidx.compose.runtime.Immutable
import com.z1.pokedex.core.network.model.google.UserData
import com.z1.pokedex.feature.home.domain.model.Pokemon

@Immutable
data class UiState(
    val userData: UserData? = null,
    val isLoading: Boolean = true,
    val isConnected: Boolean = false,
    val favorites: List<Pokemon> = emptyList()
)
