package com.z1.pokedex.feature.subscription.presentation.screen.viewmodel

sealed class SubscriptionScreenEvent {
    data class Subscribe(val userId: String) : SubscriptionScreenEvent()
}