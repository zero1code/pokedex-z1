package com.z1.pokedex.feature.home.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataEvent
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.feature.home.presentation.screen.HomeScreen
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeContainer(
    modifier: Modifier = Modifier,
    navigateToLogin: () -> Unit,
    drawerNavigateTo: (String) -> Unit
) {
    val userDataViewModel: UserDataViewModel = getViewModel()
    val userDataState = userDataViewModel.state.collectAsStateWithLifecycle()

    val viewmodel: HomeViewModel = getViewModel()
    val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
    val onEvent = viewmodel::onEvent

    HomeScreen(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background),
        userData = userDataState.value,
        uiState = uiState.value,
        onEvent = { newEvent -> onEvent(newEvent) },
        onLogoutClick = {
            userDataViewModel.onEvent(UserDataEvent.SignOut)
            navigateToLogin()
        },
        drawerNavigation = { route ->
            drawerNavigateTo(route)
        }
    )
}