@file:OptIn(ExperimentalFoundationApi::class)

package com.z1.pokedex.feature.home.presentation.screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.components.CustomLazyList
import com.z1.pokedex.designsystem.components.CustomLoadingScreen
import com.z1.pokedex.designsystem.components.ImageWithShadow
import com.z1.pokedex.designsystem.components.ListType
import com.z1.pokedex.designsystem.components.CustomTopAppBar
import com.z1.pokedex.designsystem.extensions.normalizedItemPosition
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.feature.home.presentation.model.Pokemon
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.Event
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.UiState
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEvent: (Event) -> Unit,
) {
    var isShowGridList by remember {
        mutableStateOf(false)
    }

    val threshold = remember { 5 }
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
    val animationRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
        ),
        label = "animationRotation"
    )

    val gridListState = rememberLazyGridState()
    val listState = rememberLazyListState()

    val isLastItemVisible by remember {
        derivedStateOf {
            if (isShowGridList) {
                val layoutInfo =
                    gridListState.layoutInfo
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex =
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
                lastVisibleItemIndex > (totalItems - threshold)
            } else {
                val layoutInfo =
                    listState.layoutInfo
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex =
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
                lastVisibleItemIndex > (totalItems - threshold)
            }
        }
    }

    LaunchedEffect(key1 = isLastItemVisible, key2 = uiState.isLoadingPage) {
        uiState.pokemonPage.nextPage?.let {
            if (isLastItemVisible && uiState.isLoadingPage.not()) {
                onEvent(Event.LoadNextPage)
            }
        }
    }

    AnimatedVisibility(
        visible = uiState.isFirstLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CustomLoadingScreen()
    }

    AnimatedVisibility(
        visible = uiState.isFirstLoading.not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {

        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            CustomLazyList(
                modifier = modifier,
                data = uiState.pokemonPage.pokemonList,
                listState = listState,
                gridListState = gridListState,
                listType =
                if (isShowGridList) ListType.GRID_VERTICAL
                else ListType.VERTICAL,
                headerContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = "Escolha um pokemon",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                key = { _, pokemon -> pokemon.name },
                contentPadding = PaddingValues(16.dp),
                itemContent = { _, item ->
                    if (isShowGridList) {
                        PokemonItemGrid(pokemon = item)
                    } else {
                        PokemonItemVertical(
                            imageModifier = Modifier
                                .graphicsLayer {
                                    val value =
                                        1 - (listState.layoutInfo.normalizedItemPosition(item.name).absoluteValue * 0.25F)
                                    //alpha = value
                                    scaleX = value
                                    scaleY = value
                                },
                            pokemon = item,
                        )
                    }
                },
                isLastPage = uiState.pokemonPage.nextPage == null,
                isLoadingPage = uiState.isLoadingPage,
                loadingContent = {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    ) {
                        val (image, text) = createRefs()

                        Image(
                            modifier = Modifier
                                .constrainAs(image) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                }
                                .size(100.dp)
                                .rotate(animationRotation),
                            painter = painterResource(id = R.drawable.pokeball_placeholder),
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier
                                .constrainAs(text) {
                                    top.linkTo(image.bottom, 16.dp)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom, 16.dp)
                                    start.linkTo(parent.start)
                                },
                            text = "Carregando mais pokemons...",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
                footerContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Você viu todos os pokemons, parabéns!",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            )
            CustomTopAppBar(
                isShowGridList = isShowGridList,
                onActionClick = {
                    isShowGridList = !isShowGridList
                }
            )
        }
    }
}

@Composable
fun PokemonItemVertical(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val brush = Brush.linearGradient(
        colors = listOf(Color(pokemon.dominantColor()), Color(pokemon.vibrantDarkColor())),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        val (image, name, card, canva, textNumber) = createRefs()
        Box(
            modifier = Modifier
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(30.dp)
                )
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .alpha(0.4f)
                    .padding(16.dp)
                    .graphicsLayer {

                    },
                painter = painterResource(id = R.drawable.pokeball_placeholder),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = ""
            )
        }

        Canvas(modifier = imageModifier
            .size(150.dp)
            .constrainAs(canva) {
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
                start.linkTo(image.start)
                end.linkTo(image.end)
            }
            .graphicsLayer {
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
            pokemon.palette?.let {
                drawCircle(Color(0xFFFFFFFF))
            }
        }

        pokemon.image?.asImageBitmap()?.let {
            ImageWithShadow(
                modifier = imageModifier
                    .requiredWidth(150.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 16.dp)
                    }
                    .scale(1.3f),
                imageBitmap = it,
                contentScale = ContentScale.Fit
            )
        }

        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .constrainAs(name) {
                    start.linkTo(card.start, 16.dp)
                    bottom.linkTo(card.bottom, 16.dp)
                },
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            modifier = Modifier
                .constrainAs(textNumber) {
                    top.linkTo(card.top, 16.dp)
                    start.linkTo(card.start, 16.dp)
                },
            text = String.format("#%03d", pokemon.getIndex()),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun PokemonItemGrid(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    val brush = Brush.linearGradient(
        colors = listOf(Color(pokemon.dominantColor()), Color(pokemon.vibrantDarkColor())),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        val (image, name, card, canva, textNumber) = createRefs()
        Box(
            modifier = Modifier
                .background(
                    brush = brush,
                    shape = RoundedCornerShape(16.dp)
                )
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(150.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .alpha(0.4f)
                    .padding(8.dp)
                    .graphicsLayer {

                    },
                painter = painterResource(id = R.drawable.pokeball_placeholder),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = ""
            )
        }

        Canvas(modifier = Modifier
            .size(50.dp)
            .constrainAs(canva) {
                top.linkTo(image.top)
                bottom.linkTo(image.bottom)
                start.linkTo(image.start)
                end.linkTo(image.end)
            }
            .graphicsLayer {
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
            pokemon.palette?.let {
                drawCircle(Color(0xFFFFFFFF))
            }
        }

        pokemon.image?.asImageBitmap()?.let {
            ImageWithShadow(
                modifier = Modifier
                    .requiredWidth(60.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, 16.dp)
                    }
                    .scale(1.3f),
                imageBitmap = it,
                contentScale = ContentScale.Fit
            )
        }

        Text(
            modifier = Modifier
                .padding(end = 8.dp)
                .constrainAs(name) {
                    start.linkTo(card.start, 8.dp)
                    bottom.linkTo(card.bottom, 8.dp)
                },
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
        )

        Text(
            modifier = Modifier
                .constrainAs(textNumber) {
                    start.linkTo(card.start, 8.dp)
                    top.linkTo(card.top, 8.dp)
                },
            text = String.format("#%03d", pokemon.getIndex()),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview
@Composable
fun PokemonItemHorizontalPreview() {
    PokedexZ1Theme {
        PokemonItemGrid(
            pokemon = Pokemon(
                "Pikachu",
                "https://pokeapi.co/api/v2/pokemon/1/"
            )
        )
    }
}

@Preview
@Composable
fun PokemonItemVerticalPreview() {
    PokedexZ1Theme {
        PokemonItemVertical(
            pokemon = Pokemon(
                "Pikachu",
                "https://pokeapi.co/api/v2/pokemon/1/"
            )
        )
    }
}