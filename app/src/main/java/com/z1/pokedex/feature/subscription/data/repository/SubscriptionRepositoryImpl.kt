package com.z1.pokedex.feature.subscription.data.repository

import com.z1.pokedex.feature.subscription.data.datasource.remote.SubscriptionRemoteDataSource
import com.z1.pokedex.feature.subscription.domain.repository.SubscriptionRepository

class SubscriptionRepositoryImpl(
    private val subscriptionRemoteDataSource: SubscriptionRemoteDataSource
) : SubscriptionRepository {
    override fun checkSubscriptionStatus(subscriptionPlanId: String, accountId: String) =
        subscriptionRemoteDataSource.checkSubscriptionStatus(subscriptionPlanId, accountId)
}