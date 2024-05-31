package com.z1.pokedex.core.network.services.googleauth

import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.z1.pokedex.BuildConfig
import com.z1.pokedex.core.common.model.google.SignResult
import com.z1.pokedex.core.common.model.google.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthClientImpl(
    private val oneTapClient: SignInClient
) : GoogleAuthClient {
    private val auth = Firebase.auth
    private val _signedInUser = MutableStateFlow(SignResult())
    override val signedInUser: StateFlow<SignResult> get() = _signedInUser.asStateFlow()
    override suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    override suspend fun signInWithIntent(intent: Intent) {
        runCatching {
            val credential = oneTapClient.getSignInCredentialFromIntent(intent)
            val googleTokenId = credential.googleIdToken
            val googleCredentials = GoogleAuthProvider.getCredential(googleTokenId, null)
            auth.signInWithCredential(googleCredentials).await().user
        }.onSuccess { user ->
            user?.run {
                _signedInUser.update {
                    SignResult(
                        data = UserData(
                            userId = uid,
                            userName = displayName,
                            profilePictureUrl = photoUrl?.toString()
                        ),
                        message = null
                    )
                }
            } ?: run {
                _signedInUser.update {
                    SignResult(
                        data = null,
                        message = "User not found"
                    )
                }
            }
        }.onFailure { e ->
            _signedInUser.update {
                SignResult(
                    data = null,
                    message = e.message
                )
            }
        }
    }

    override suspend fun signOut() {
        runCatching {
            oneTapClient.signOut().await()
            auth.signOut()
        }.onSuccess {
            _signedInUser.update {
                SignResult(data = null, message = "User signed out successful")
            }
        }.onFailure { e ->
            _signedInUser.update {
                SignResult(data = null, message = e.message)
            }
        }
    }

    override fun getSignedInUser() {
        auth.currentUser?.run {
            _signedInUser.update {
                it.copy(
                    data = UserData(
                        userId = uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    ),
                    message = null
                )
            }
        } ?: run {
            _signedInUser.update {
                it.copy(
                    data = null,
                    message = "No user signed in"
                )
            }
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}