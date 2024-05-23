package com.z1.pokedex.feature.subscription.data.datasource.remote

interface SubscriptionRemoteDataSource {
    fun checkSubscriptionStatus(subscriptionPlanId: String, accountId: String)
}