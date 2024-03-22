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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.designsystem.components.CustomLazyList
import com.z1.pokedex.designsystem.components.CustomLoadingScreen
import com.z1.pokedex.designsystem.components.CustomTopAppBar
import com.z1.pokedex.designsystem.components.ImageWithShadow
import com.z1.pokedex.designsystem.components.ListType
import com.z1.pokedex.designsystem.extensions.normalizedItemPosition
import com.z1.pokedex.designsystem.theme.IPokemonDimensions
import com.z1.pokedex.designsystem.theme.LocalGridPokemonSpacing
import com.z1.pokedex.designsystem.theme.LocalPokemonSpacing
import com.z1.pokedex.designsystem.theme.LocalSpacing
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

    LaunchedEffect(key1 = true) {
        onEvent(Event.LoadNextPage)
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
        PokemonList(
            modifier = modifier,
            uiState = uiState,
            onEvent = { onEvent(it) },
            isShowGridList = isShowGridList,
            onLayoutListChange = { isShowGridList = it },
            onPokemonClick = { clickedPokemon ->

            }
        )
    }
}

@Composable
fun PokemonList(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEvent: (Event) -> Unit,
    isShowGridList: Boolean,
    onLayoutListChange: (Boolean) -> Unit,
    onPokemonClick: (pokemon: Pokemon) -> Unit
) {
    val spacing = LocalSpacing.current
    val pokemonDimensions =
        if (isShowGridList) LocalGridPokemonSpacing.current
        else LocalPokemonSpacing.current

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

    val threshold = remember { 5 }
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

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        CustomLazyList(
            data = uiState.pokemonPage.pokemonList,
            listState = listState,
            gridListState = gridListState,
            listType =
            if (isShowGridList) ListType.GRID_VERTICAL
            else ListType.VERTICAL,
            headerContent = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = pokemonDimensions.headerSize,
                            bottom = spacing.medium
                        ),
                    text = stringResource(id = R.string.label_select_pokemon),

                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            },
            key = { _, pokemon -> pokemon.name },
            contentPadding = PaddingValues(spacing.medium),
            itemContent = { _, item ->
                val imageModifier =
                    if (isShowGridList) Modifier
                    else {
                        Modifier
                            .graphicsLayer {
                                val value =
                                    1 - (listState.layoutInfo.normalizedItemPosition(item.name).absoluteValue * 0.25F)
                                //alpha = value
                                scaleX = value
                                scaleY = value
                            }
                    }
                PokemonItem(
                    dimen = pokemonDimensions,
                    isShowGridList = isShowGridList,
                    imageModifier = imageModifier,
                    pokemon = item,
                    onPokemonClick = { clickedPokemon ->
                        onPokemonClick(clickedPokemon)
                    }
                )
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
                            .size(pokemonDimensions.footerSize)
                            .rotate(animationRotation),
                        painter = painterResource(id = R.drawable.pokeball_placeholder),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(text) {
                                top.linkTo(image.bottom, spacing.medium)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom, spacing.medium)
                                start.linkTo(parent.start)
                            },
                        text = stringResource(id = R.string.label_loading_more_pokemon),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            },
            footerContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.medium)
                        .height(pokemonDimensions.footerSize),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.label_saw_all_pokemon),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
        )
        CustomTopAppBar(
            isShowGridList = isShowGridList,
            onActionClick = { onLayoutListChange(!isShowGridList) }
        )
    }
}

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    pokemon: Pokemon,
    dimen: IPokemonDimensions,
    isShowGridList: Boolean,
    onPokemonClick: (pokemon: Pokemon) -> Unit
) {

    val brush = Brush.linearGradient(
        colors = listOf(Color(pokemon.dominantColor()), Color(pokemon.vibrantDarkColor())),
        start = Offset(0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0f)
    )
    ConstraintLayout(
        modifier
            .fillMaxWidth()
            .padding(vertical = dimen.normal)
    ) {
        val (image, name, card, canva, textNumber) = createRefs()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(dimen.brushShape))
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(dimen.boxSize)
                .drawBehind {
                    drawRoundRect(brush = brush)
                }
                .clickable { onPokemonClick(pokemon) }
        ) {
            Image(
                modifier = Modifier
                    .size(dimen.imagePlaceHolderSize)
                    .alpha(0.4f)
                    .padding(dimen.normal),
                painter = painterResource(id = R.drawable.pokeball_placeholder),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = ""
            )
        }

        Canvas(modifier = imageModifier
            .size(dimen.canvaSize)
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
                    .requiredWidth(dimen.imageSize)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, dimen.imageMarginEnd)
                    }
                    .scale(1.3f),
                imageBitmap = it,
                contentScale = ContentScale.Fit,
                offsetX = dimen.offsetX,
                offsetY = dimen.offsetY
            )
        }

        Text(
            modifier = Modifier
                .padding(end = dimen.normal)
                .constrainAs(name) {
                    start.linkTo(card.start, dimen.normal)
                    bottom.linkTo(card.bottom, dimen.normal)
                },
            text = pokemon.name,
            color = MaterialTheme.colorScheme.onSurface,
            style =
            if (isShowGridList) MaterialTheme.typography.titleSmall
            else MaterialTheme.typography.titleMedium,
        )

        Text(
            modifier = Modifier
                .constrainAs(textNumber) {
                    top.linkTo(card.top, dimen.normal)
                    start.linkTo(card.start, dimen.normal)
                },
            text = String.format("#%03d", pokemon.getIndex()),
            color = MaterialTheme.colorScheme.onSurface,
            style =
            if (isShowGridList) MaterialTheme.typography.titleSmall
            else MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
fun PokemonItemVerticalPreview() {
    PokedexZ1Theme {
        PokemonItem(
            pokemon = Pokemon(
                "Pikachu",
                "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            dimen = LocalPokemonSpacing.current,
            isShowGridList = false,
            onPokemonClick = { _ ->}
        )
    }
}