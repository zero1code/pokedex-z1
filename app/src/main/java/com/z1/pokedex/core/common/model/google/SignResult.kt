package com.z1.pokedex.core.common.model.google

import androidx.compose.runtime.Immutable

@Immutable
data class SignResult(
    val data: UserData? = null,
    val message: String? = null
)

@Immutable
data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?
)