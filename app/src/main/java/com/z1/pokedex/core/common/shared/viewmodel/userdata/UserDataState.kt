package com.z1.pokedex.core.common.shared.viewmodel.userdata

import androidx.compose.runtime.Immutable
import com.z1.pokedex.core.network.model.google.UserData

@Immutable
data class UserDataState(
    val data: UserData? = null,
    val message: String? = null,
    val isConnected: Boolean = false
) {
    fun isLoading() = data == null && message == null
}
