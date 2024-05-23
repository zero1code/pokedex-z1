package com.z1.pokedex.core.network.services.googlebilling

import com.z1.pokedex.core.common.model.google.SubscriptionState
import kotlinx.coroutines.flow.StateFlow

interface GoogleBillingClient {
    val subscriptionState: StateFlow<SubscriptionState>
    fun checkSubscriptionStatus(subscriptionPlanId: String, accountId: String)
}