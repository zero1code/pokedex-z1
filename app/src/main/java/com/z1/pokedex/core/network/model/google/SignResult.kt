package com.z1.pokedex.core.network.model.google

data class SignResult(
    val data: UserData? = null,
    val message: String? = null
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUrl: String?
)
