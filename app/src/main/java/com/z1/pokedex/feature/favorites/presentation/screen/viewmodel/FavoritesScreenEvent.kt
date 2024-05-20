package com.z1.pokedex.feature.favorites.presentation.screen.viewmodel

sealed class FavoritesScreenEvent {
    data class GetFavorites(val userId: String): FavoritesScreenEvent()
    data object SignedInUser: FavoritesScreenEvent()
}