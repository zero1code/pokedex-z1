package com.z1.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataEvent
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataState
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataViewModel
import com.z1.pokedex.core.navigation.navgraph.NavGraph
import com.z1.pokedex.core.navigation.register.NavigationGraph
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    private val userDataViewModel: UserDataViewModel by viewModel(parameters = {
        parametersOf(this)
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        var userDataState: UserDataState by mutableStateOf(UserDataState())
        var isFirstOpen = true
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                userDataViewModel.state.onEach { userData ->
                    userDataState = userData
                    isFirstOpen = userData.data == null
                }.collect()
            }
        }
        setContent {
            LaunchedEffect(key1 = Unit) {
                userDataViewModel.onEvent(UserDataEvent.GetSignedInUser)
            }
            PokedexZ1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF7D02C)
                ) {
                    val navGraph = get<NavGraph>()
                    AnimatedVisibility(
                        visible = userDataState.isLoading(),
                        enter = fadeIn(),
                        exit = scaleOut()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.picachu_face),
                            contentDescription = null,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit
                        )
                    }
                    AnimatedVisibility(
                        visible = userDataState.isLoading().not(),
                        enter = fadeIn(),
                        exit = ExitTransition.None
                    ) {
                        Box(
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.picachu_face),
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )
                            PokedexZ1App(
                                navGraph = navGraph,
                                isSignedIn = userDataState.data != null && isFirstOpen
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokedexZ1App(
    modifier: Modifier = Modifier,
    navGraph: NavGraph,
    isSignedIn: Boolean
) {
    NavigationGraph(
        modifier = modifier,
        navGraph = navGraph,
        isLogged = isSignedIn
    )
}