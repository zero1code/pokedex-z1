package com.z1.pokedex.feature.login.presentation.screen.viewmodel

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
