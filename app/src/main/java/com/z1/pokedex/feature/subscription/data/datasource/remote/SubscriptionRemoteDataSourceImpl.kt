package com.z1.pokedex.feature.subscription.data.datasource.remote

import com.z1.pokedex.core.network.services.googlebilling.GoogleBillingClient

class SubscriptionRemoteDataSourceImpl(
    private val googleBillingClient: GoogleBillingClient
): SubscriptionRemoteDataSource {
    override fun checkSubscriptionStatus(subscriptionPlanId: String, accountId: String) =
        googleBillingClient.checkSubscriptionStatus(subscriptionPlanId, accountId)
}