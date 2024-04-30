package com.z1.pokedex.core.network.service.googleauth

import android.content.Intent
import android.content.IntentSender
import com.z1.pokedex.core.network.model.google.SignResult
import com.z1.pokedex.core.network.model.google.UserData

interface GoogleAuthClient {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignResult
    suspend fun signOut()
    fun getSignedInUser(): UserData?
}