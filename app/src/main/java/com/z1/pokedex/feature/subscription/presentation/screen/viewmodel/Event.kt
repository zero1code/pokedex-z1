package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

sealed class Event {
    data object SignedUser: Event()
}