package com.z1.pokedex.feature.subscription.domain.repository

interface SubscriptionRepository {
    fun checkSubscriptionStatus(subscriptionPlanId: String, accountId: String)
}