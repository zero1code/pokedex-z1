package com.z1.pokedex.core.network.service.googleauth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
