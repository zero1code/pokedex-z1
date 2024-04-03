package com.z1.pokedex.feature.home.presentation.screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.components.AnimatedText
import com.z1.pokedex.designsystem.components.CustomTopAppBar
import com.z1.pokedex.designsystem.components.ImageWithShadow
import com.z1.pokedex.designsystem.theme.LocalPokemonSpacing
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.Event
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

enum class PokemonScale {
    STAND_BY,
    START,
    FINISH
}

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onNavigationIconClick: () -> Unit,
    onEvent: (Event) -> Unit
) {

    LaunchedEffect(key1 = true) {
        delay(500)
        onEvent(Event.UpdateSelectedPokemon(pokemon.name))
    }

    val brush = Brush.linearGradient(
        colors = listOf(Color(pokemon.dominantColor()), Color(pokemon.vibrantDarkColor())),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(brush = brush)
            },
        contentAlignment = Alignment.TopCenter
    ) {
        PokemonDetail(pokemon = pokemon)
        Header(
            onNavigationIconClick = onNavigationIconClick
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    CustomTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClick
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.label_pokemon_details),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        actions = {
            IconButton(
                onClick = {  }
            ) {
                Image(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = ""
                )
            }
        }
    )
}

@Composable
fun PokemonDetail(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val dimen = LocalPokemonSpacing.current

    var pokemonScaleState: PokemonScale by remember { mutableStateOf(PokemonScale.STAND_BY) }

    LaunchedEffect(key1 = true) {
        pokemonScaleState = PokemonScale.START
    }
    val scalePokemon by animateFloatAsState(
        targetValue = when (pokemonScaleState) {
            PokemonScale.STAND_BY -> 0f
            PokemonScale.START -> 1.7f
            PokemonScale.FINISH -> 1.3f
        },
        animationSpec = tween(
            durationMillis = when (pokemonScaleState) {
                PokemonScale.STAND_BY -> 0
                PokemonScale.START -> TimeUnit.SECONDS.toMillis(2)
                PokemonScale.FINISH -> TimeUnit.SECONDS.toMillis(2)
            }.toInt(),
            easing = FastOutSlowInEasing
        ),
        finishedListener = { pokemonScaleState = PokemonScale.FINISH },
        label = "scale-pokemon"
    )

    val brush = Brush.linearGradient(
        colors = listOf(Color(pokemon.vibrantDarkColor()), Color(pokemon.dominantColor())),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )

    val infiniteTransition = rememberInfiniteTransition("scale-inifinte")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, 0, EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale-infinite"
    )

    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(top = 80.dp, start = 16.dp, end = 16.dp)
            .statusBarsPadding()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(dimen.brushShape)
            )

    ) {
        val (image, name, canva, textNumber) = createRefs()

        Canvas(modifier = Modifier
            .size(200.dp)
            .constrainAs(canva) {
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
                start.linkTo(image.start)
                end.linkTo(image.end)
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val blur = 150f
                    renderEffect = RenderEffect
                        .createBlurEffect(
                            blur, blur, Shader.TileMode.DECAL
                        )
                        .asComposeRenderEffect()
                }
            }
        ) {
            drawCircle(brush)
        }

        Text(
            modifier = Modifier
                .constrainAs(textNumber) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                },
            text = String.format("#%03d", pokemon.getIndex()),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
        )

        pokemon.image?.asImageBitmap()?.let {
            ImageWithShadow(
                modifier = Modifier
                    .requiredWidth(150.dp)
                    .constrainAs(image) {
                        top.linkTo(textNumber.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .scale(scalePokemon),
                imageBitmap = it,
                contentScale = ContentScale.Fit,
                offsetX = 5.dp,
                offsetY = 5.dp
            )
        }

        AnimatedText(
            modifier = Modifier
                .alpha(
                    if (pokemonScaleState == PokemonScale.FINISH) 1f
                    else 0f
                )
                .constrainAs(name) {
                    top.linkTo(canva.bottom, 64.dp)
                    start.linkTo(canva.start)
                    end.linkTo(canva.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
            useAnimation = true,
            animationDelay = TimeUnit.SECONDS.toMillis(4),
            text = pokemon.name
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF44336)
@Composable
private fun PokemonDetailsPreview() {
    PokedexZ1Theme {
        PokemonDetailsScreen(
            pokemon = Pokemon("Picachu", "https://pokeapi.co/api/v2/pokemon/1/"),
            onNavigationIconClick = {},
            onEvent = {}
        )
    }
}