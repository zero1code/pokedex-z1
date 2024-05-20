package com.z1.pokedex.feature.favorites.presentation.screen.viewmodel

sealed class Event {
    data class GetFavorites(val userId: String): Event()
    data object SignedInUser: Event()
}