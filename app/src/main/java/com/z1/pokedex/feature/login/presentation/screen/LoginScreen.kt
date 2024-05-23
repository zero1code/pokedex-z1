package com.z1.pokedex.feature.login.presentation.screen

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.core.common.designsystem.theme.CoralRed
import com.z1.pokedex.core.common.designsystem.theme.CustomRippleTheme
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    userDataState: UserDataState,
    onSignInClick: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-transition")
    val animationRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30_000, easing = LinearEasing),
        ),
        label = "animation-rotation"
    )

    val color = remember { Animatable(CoralRed) }
    LaunchedEffect(Unit) {
        color.animateTo(Color(0xFFA8A77A), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFEE8130), animationSpec = tween(5_000))
        color.animateTo(Color(0xFF6390F0), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFF7D02C), animationSpec = tween(5_000))
        color.animateTo(Color(0xFF7AC74C), animationSpec = tween(5_000))
        color.animateTo(Color(0xFF96D9D6), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFC22E28), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFA33EA1), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFE2BF65), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFA98FF3), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFF95587), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFA6B91A), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFB6A136), animationSpec = tween(5_000))
        color.animateTo(Color(0xFF735797), animationSpec = tween(5_000))
        color.animateTo(Color(0xFF6F35FC), animationSpec = tween(5_000))
        color.animateTo(Color(0xFF705746), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFB7B7CE), animationSpec = tween(5_000))
        color.animateTo(Color(0xFFD685AD), animationSpec = tween(5_000))
        color.animateTo(CoralRed, animationSpec = tween(5_000))
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = userDataState.message) {
        userDataState.message?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(key1 = userDataState.data) {
        if (userDataState.data != null) navigateToHomeScreen()
    }

    ConstraintLayout(
        modifier = modifier.fillMaxSize()
    ) {
        val (bgImage, logoImage, textWelcome, textAppName, button) = createRefs()

        Image(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(bgImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            alpha = 0.1f,
            painter = painterResource(id = R.drawable.bg_pokemon_login),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Image(
            modifier = Modifier
                .constrainAs(logoImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(textAppName.top)
                    end.linkTo(parent.end)
                }
                .rotate(animationRotation),
            painter = painterResource(id = R.drawable.pokeball_placeholder),
            colorFilter = ColorFilter.tint(color.value),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .constrainAs(textWelcome) {
                    start.linkTo(parent.start)
                    bottom.linkTo(textAppName.top)
                    end.linkTo(parent.end)
                },
            text = stringResource(id = R.string.label_welcome),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            modifier = Modifier
                .constrainAs(textAppName) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )

        CompositionLocalProvider(
            LocalRippleTheme provides CustomRippleTheme(Color.White)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = PokedexZ1Theme.dimen.medium
                    )
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        top.linkTo(textAppName.bottom)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                onClick = onSignInClick
            ) {
                Image(
                    modifier = Modifier
                        .size(PokedexZ1Theme.dimen.large),
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .padding(start = PokedexZ1Theme.dimen.medium),
                    text = stringResource(id = R.string.label_signin_with_google)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PokedexZ1Theme {
        LoginScreen(
            userDataState = UserDataState(),
            onSignInClick = {},
            navigateToHomeScreen = {}
        )
    }
}