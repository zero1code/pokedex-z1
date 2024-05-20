package com.z1.pokedex.feature.home.presentation.screen

import androidx.compose.runtime.Immutable
import com.z1.pokedex.core.network.model.google.UserData
import com.z1.pokedex.feature.home.domain.model.Pokemon

@Immutable
data class HomeScreenUiState(
    val pokemonPage: List<Pokemon> = emptyList(),
    val isLastPage: Boolean = false,
    val isLoadingPage: Boolean = false,
    val isFirstLoading: Boolean = true,
    val isConnected: Boolean = false,
    val pokemonClickedList: Set<String> = emptySet(),
    val userData: UserData? = UserData("", null, null)
) {
    fun canLoadNextPage() = isLoadingPage.not() && isLastPage.not()
}
