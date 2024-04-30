package com.z1.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.z1.pokedex.core.network.service.googleauth.GoogleAuthClient
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.navigation.register.NavigationGraph
import com.z1.pokedex.navigation.navgraph.NavGraph
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
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navGraph = get<NavGraph>()
                    PokedexZ1App(
                        navGraph = navGraph,
                        isSignedIn = googleAuthClient.getSignedInUser() != null
                    )
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