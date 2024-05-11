package com.z1.pokedex.feature.home.presentation.screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.components.AnimatedText
import com.z1.pokedex.designsystem.components.CustomLoading
import com.z1.pokedex.designsystem.components.CustomLinearProgress
import com.z1.pokedex.designsystem.components.CustomShineImage
import com.z1.pokedex.designsystem.components.CustomTopAppBar
import com.z1.pokedex.designsystem.components.ImageWithShadow
import com.z1.pokedex.designsystem.theme.CelticBlue
import com.z1.pokedex.designsystem.theme.CoralRed
import com.z1.pokedex.designsystem.theme.Glacier
import com.z1.pokedex.designsystem.theme.LocalPokemonSpacing
import com.z1.pokedex.designsystem.theme.MediumSeaGreen
import com.z1.pokedex.designsystem.theme.OrangePeel
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.PokemonDetails.Companion.MAX_ATTACK
import com.z1.pokedex.feature.home.domain.model.PokemonDetails.Companion.MAX_DEFENSE
import com.z1.pokedex.feature.home.domain.model.PokemonDetails.Companion.MAX_EXP
import com.z1.pokedex.feature.home.domain.model.PokemonDetails.Companion.MAX_HP
import com.z1.pokedex.feature.home.domain.model.PokemonDetails.Companion.MAX_SPEED
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.Event
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.UiState
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun PokemonDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    pokemon: Pokemon,
    pokemonDetails: PokemonDetails?,
    onNavigationIconClick: () -> Unit,
    onEvent: (Event) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        delay(500)
        onEvent(Event.UpdateSelectedPokemon(pokemon.name))
    }

    LaunchedEffect(key1 = uiState.isConnected) {
        if (uiState.isConnected) onEvent(Event.GetPokemonDetails(pokemon.name))
    }

    val colors = listOf(Color(pokemon.dominantColor()), Color(pokemon.vibrantDarkColor()))
    val brush = Brush.linearGradient(
        colors = colors,
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
        Column {
            PokemonCard(pokemon = pokemon)
            PokemonDetailsCard(
                uiState = uiState,
                chipColor = colors,
                pokemonDetails = pokemonDetails
            )
        }
        Header(
            onNavigationIconClick = onNavigationIconClick
        )
    }
}

@Composable
private fun Header(
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
                onClick = { }
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
private fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val scalePokemon by remember { mutableStateOf(Animatable(0f)) }
    LaunchedEffect(key1 = Unit) {
        scalePokemon.animateTo(1.7f, animationSpec = tween(durationMillis = 2_000))
        scalePokemon.animateTo(1.3f, animationSpec = tween(durationMillis = 2_000))
    }

    val dimen = LocalPokemonSpacing.current

    val colors = listOf(Color(pokemon.vibrantDarkColor()), Color(pokemon.dominantColor()))

    val infiniteTransition = rememberInfiniteTransition("scale-infinite")
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
            .padding(
                top = PokedexZ1Theme.dimen.topBar,
                start = PokedexZ1Theme.dimen.medium,
                end = PokedexZ1Theme.dimen.medium
            )
            .statusBarsPadding()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(dimen.brushShape)
            )

    ) {
        val (image, name, canva, textNumber) = createRefs()

        CustomShineImage(
            modifier = Modifier
                .constrainAs(canva) {
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                },
            colors = colors,
            size = 200.dp,
            scale = scale
        )

        Text(
            modifier = Modifier
                .padding(
                    top = PokedexZ1Theme.dimen.medium,
                    start = PokedexZ1Theme.dimen.medium
                )
                .constrainAs(textNumber) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
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
                    .scale(scalePokemon.value),
                imageBitmap = it,
                contentScale = ContentScale.Fit,
                offsetX = 5.dp,
                offsetY = 5.dp
            )
        }

        AnimatedText(
            modifier = Modifier
                .padding(
                    top = PokedexZ1Theme.dimen.large,
                    bottom = PokedexZ1Theme.dimen.medium
                )
                .constrainAs(name) {
                    top.linkTo(canva.bottom)
                    start.linkTo(canva.start)
                    end.linkTo(canva.end)
                    bottom.linkTo(parent.bottom)
                },
            useAnimation = true,
            animationDelay = TimeUnit.SECONDS.toMillis(4),
            text = pokemon.name
        )
    }
}

@Composable
private fun PokemonDetailsCard(
    modifier: Modifier = Modifier,
    uiState: UiState,
    chipColor: List<Color>,
    pokemonDetails: PokemonDetails?
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (title, card) = createRefs()

        Text(
            modifier = Modifier
                .padding(
                    top = PokedexZ1Theme.dimen.large
                )
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            text = "Basics statistics",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )

        Card(
            modifier = Modifier
                .constrainAs(card) {
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .padding(horizontal = PokedexZ1Theme.dimen.medium),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {

            AnimatedContent(
                targetState = pokemonDetails,
                transitionSpec = {
                    scaleIn() togetherWith scaleOut()
                },
                label = "pokemon-details"
            ) { details ->

                if (details != null) {
                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = PokedexZ1Theme.dimen.medium
                            )
                            .heightIn(min = 300.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        PokemonType(
                            chipColor = chipColor,
                            types = details.types
                        )
                        PhysicsDetails(
                            weight = details.getWeightString(),
                            height = details.getHeightString()
                        )
                        HabilityDetails(details)
                    }
                } else {
                    CustomLoading(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp),
                        iconSize = PokedexZ1Theme.dimen.loadingIcon,
                        animateIcon = true,
                        animateVelocity =
                        if (uiState.isConnected) 1_000
                        else 5_000,
                        loadingMessage =
                        if (uiState.isConnected) R.string.label_loading_pokemon_details
                        else R.string.label_connection_lost
                    )
                }

            }
        }
    }
}

@Composable
fun PokemonType(
    modifier: Modifier = Modifier,
    chipColor: List<Color>,
    types: List<PokemonDetails.Type>
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-transition")
    val color by infiniteTransition.animateColor(
        initialValue = chipColor[0],
        targetValue = chipColor[1],
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "chip-color"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(PokedexZ1Theme.dimen.normal),
        verticalAlignment = Alignment.CenterVertically
    ) {
        types.forEach { type ->
            SuggestionChip(
                modifier = modifier,
                shape = MaterialTheme.shapes.large,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = color
                ),
                border = null,
                label = {
                    Text(
                        text = type.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                onClick = {},
            )
        }
    }
}

@Composable
fun PhysicsDetails(
    modifier: Modifier = Modifier,
    weight: String,
    height: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        PhysicsDetailsItem(
            label = R.string.label_weight,
            value = weight
        )
        PhysicsDetailsItem(
            label = R.string.label_height,
            value = height
        )
    }
}

@Composable
private fun PhysicsDetailsItem(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall,
        )
        Text(
            text = stringResource(id = label),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun HabilityDetails(details: PokemonDetails) {
    Column(
        verticalArrangement = Arrangement.spacedBy(PokedexZ1Theme.dimen.normal)
    ) {
        CustomLinearProgress(
            statisticsLabel = R.string.label_hp,
            progressColor = MediumSeaGreen,
            currentProgress = details.hp,
            maxProgress = MAX_HP

        )
        CustomLinearProgress(
            statisticsLabel = R.string.label_atk, progressColor = CoralRed,
            currentProgress = details.attack,
            maxProgress = MAX_ATTACK
        )
        CustomLinearProgress(
            statisticsLabel = R.string.label_def,
            progressColor = CelticBlue,
            currentProgress = details.defense,
            maxProgress = MAX_DEFENSE
        )
        CustomLinearProgress(
            statisticsLabel = R.string.label_spd,
            progressColor = OrangePeel,
            currentProgress = details.speed,
            maxProgress = MAX_SPEED
        )
        CustomLinearProgress(
            statisticsLabel = R.string.label_exp,
            progressColor = Glacier,
            currentProgress = details.exp,
            maxProgress = MAX_EXP
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF44336)
@Composable
private fun PokemonCardPreview() {
    PokedexZ1Theme {
        PokemonDetailsScreen(
            uiState = UiState(),
            pokemon = Pokemon(0, "Picachu", "https://pokeapi.co/api/v2/pokemon/1/"),
            pokemonDetails = null,
            onNavigationIconClick = {},
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF44336)
@Composable
private fun PokemonDetailsPreview() {
    PokedexZ1Theme {
        PokemonDetailsPreview()
    }
}