package com.z1.pokedex

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.core.network.service.googlebilling.GoogleBillingClient
import com.z1.pokedex.core.network.service.googlebilling.LocalGoogleBillingClient
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.core.navigation.navgraph.NavGraph
import com.z1.pokedex.core.navigation.register.NavigationGraph
import org.koin.android.ext.android.get

class MainActivity : ComponentActivity() {
    private val googleAuthClient = get<GoogleAuthClient>()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PokedexZ1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF7D02C)
                ) {
                    val navGraph = get<NavGraph>()
                    CompositionLocalProvider(
                        LocalGoogleBillingClient provides GoogleBillingClient(this@MainActivity)
                    ) {
                        Box(
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Image(painter = painterResource(id = R.drawable.picachu_face),
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )
                            PokedexZ1App(
                                navGraph = navGraph,
                                isSignedIn = googleAuthClient.getSignedInUser() != null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBarColor(color: Color) {
    val view = LocalView.current
    val darkTheme = isSystemInDarkTheme()

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = color.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
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