package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

sealed class SubscriptionScreenEvent {
    data object SignedUser: SubscriptionScreenEvent()
}