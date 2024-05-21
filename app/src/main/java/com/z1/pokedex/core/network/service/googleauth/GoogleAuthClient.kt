package com.z1.pokedex.core.network.service.googleauth

import android.content.Intent
import android.content.IntentSender
import com.z1.pokedex.core.network.model.google.SignResult
import com.z1.pokedex.core.network.model.google.UserData
import kotlinx.coroutines.flow.StateFlow

interface GoogleAuthClient {
    val signedInUser: StateFlow<SignResult>
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent)
    suspend fun signOut()
    fun getSignedInUser()
}