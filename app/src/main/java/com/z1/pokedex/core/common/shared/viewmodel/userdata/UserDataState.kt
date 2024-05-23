package com.z1.pokedex.core.common.shared.viewmodel.userdata

import androidx.compose.runtime.Immutable
import com.z1.pokedex.core.common.model.google.UserData
import com.z1.pokedex.core.common.model.google.SubscriptionState

@Immutable
data class UserDataState(
    val data: UserData? = null,
    val message: String? = null,
    val subscriptionState: SubscriptionState? = null,
    val isConnected: Boolean = false
) {
    fun isLoading() = data == null && message == null

    fun isPremium() = subscriptionState?.subscriptions?.contains(data?.userId) == true
}
