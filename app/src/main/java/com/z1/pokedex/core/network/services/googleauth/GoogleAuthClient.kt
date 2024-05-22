package com.z1.pokedex.core.network.services.googleauth

import android.content.Intent
import android.content.IntentSender
import com.z1.pokedex.core.common.model.google.SignResult
import kotlinx.coroutines.flow.StateFlow

interface GoogleAuthClient {
    val signedInUser: StateFlow<SignResult>
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent)
    suspend fun signOut()
    fun getSignedInUser()
}