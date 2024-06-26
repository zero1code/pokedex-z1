package com.z1.pokedex.feature.login.presentation

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.core.network.services.googleauth.GoogleAuthClient
import com.z1.pokedex.feature.login.presentation.screen.LoginScreen
import com.z1.pokedex.feature.login.presentation.screen.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun LoginContainer(
    modifier: Modifier = Modifier,
    navigateToHomeScreen: () -> Unit
) {
    val googleAuthUiClient = get<GoogleAuthClient>()

    val activity = LocalContext.current as ComponentActivity
    val userDataViewModel: UserDataViewModel = getViewModel(parameters = {
        parametersOf(activity)
    })
    val userDataState = userDataViewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val viewModel: LoginViewModel = getViewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                scope.launch {
                    googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                }
            }
        }
    )

    fun signInRequest() {
        scope.launch {
            val signInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }

    LoginScreen(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background),
        userDataState = userDataState.value,
        onSignInClick = { signInRequest() },
        navigateToHomeScreen = navigateToHomeScreen
    )
}