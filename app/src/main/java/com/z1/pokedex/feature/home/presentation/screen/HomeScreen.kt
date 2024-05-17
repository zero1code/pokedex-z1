package com.z1.pokedex.feature.home.presentation.screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewDay
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.core.common.orZero
import com.z1.pokedex.designsystem.components.CustomIconButton
import com.z1.pokedex.designsystem.components.CustomLazyList
import com.z1.pokedex.designsystem.components.CustomLoading
import com.z1.pokedex.designsystem.components.CustomShineImage
import com.z1.pokedex.designsystem.components.CustomTopAppBar
import com.z1.pokedex.designsystem.components.ImageWithShadow
import com.z1.pokedex.designsystem.components.ListType
import com.z1.pokedex.designsystem.extensions.normalizedItemPosition
import com.z1.pokedex.designsystem.theme.CustomRippleTheme
import com.z1.pokedex.designsystem.theme.IPokemonDimensions
import com.z1.pokedex.designsystem.theme.LocalGridPokemonSpacing
import com.z1.pokedex.designsystem.theme.LocalPokemonSpacing
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.Event
import com.z1.pokedex.feature.home.presentation.screen.viewmodel.UiState
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEvent: (Event) -> Unit,
    navigateToLogin: () -> Unit,
    drawerNavigation: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    var isShowGridList by remember { mutableStateOf(false) }
    var pokemon: Pokemon? by remember { mutableStateOf(null) }

    val gridListState = rememberLazyGridState()
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = uiState.isConnected) {
        if (uiState.isConnected) onEvent(Event.LoadNextPage)
    }

    LaunchedEffect(key1 = Unit) {
        if (uiState.isConnected.not()) onEvent(Event.LoadNextPage)
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(Event.SignedUser)
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(Event.GetPokemonFavoritesName)
    }

    LaunchedEffect(key1 = uiState.userData) {
        if (uiState.userData == null) navigateToLogin()
    }

    AnimatedVisibility(
        visible = uiState.isFirstLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CustomLoading(
            modifier = Modifier.fillMaxSize(),
            iconSize = PokedexZ1Theme.dimen.loadingIcon,
            animateIcon = true,
            loadingMessage = R.string.label_loading_pokemon
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            DrawerContent(
                userData = uiState.userData,
                onNavigationItemClick = { route ->
                    drawerNavigation(route)
                    scope.launch { drawerState.close() }
                },
                onLogoutClick = {
                    onEvent(Event.Logout)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        AnimatedVisibility(
            visible = uiState.isFirstLoading.not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AnimatedContent(
                targetState = pokemon,
                transitionSpec = {
                    if (targetState != null) {
                        slideInHorizontally(
                            tween(300, 0, FastOutLinearInEasing)
                        ) { it } togetherWith
                                ExitTransition.KeepUntilTransitionsFinished

                    } else {
                        slideInHorizontally { -it } togetherWith (slideOutHorizontally { it / 3 } + fadeOut())
                    }
                },
                label = "pokemon-details"
            ) { pokemonState ->
                if (pokemonState != null) {
                    PokemonDetailsScreen(
                        uiState = uiState,
                        pokemon = pokemonState,
                        pokemonDetails = uiState.pokemonDetails,
                        onNavigationIconClick = {
                            pokemon = null
                        },
                        onEvent = { onEvent(it) }
                    )
                } else {
                    PokemonList(
                        modifier = modifier,
                        uiState = uiState,
                        listState = listState,
                        gridListState = gridListState,
                        onEvent = { onEvent(it) },
                        isShowGridList = isShowGridList,
                        onLayoutListChange = { isShowGridList = it },
                        onPokemonClick = { pokemonClicked ->
                            pokemon = pokemonClicked
                        },
                        onMenuNavigationClick = {
                            scope.launch { drawerState.open() }
                        }
                    )
                }
            }
        }
    }

    BackHandler(pokemon != null) {
        if (pokemon != null) pokemon = null
    }

    BackHandler(drawerState.isOpen) {
        if (drawerState.isOpen) scope.launch { drawerState.close() }
    }
}

@Composable
private fun PokemonList(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEvent: (Event) -> Unit,
    listState: LazyListState,
    gridListState: LazyGridState,
    isShowGridList: Boolean,
    onLayoutListChange: (Boolean) -> Unit,
    onPokemonClick: (pokemon: Pokemon) -> Unit,
    onMenuNavigationClick: () -> Unit
) {

    val threshold = remember { 5 }
    val isLastItemVisible by if (isShowGridList) {
        remember {
            derivedStateOf {
                val layoutInfo =
                    gridListState.layoutInfo
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex =
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index.orZero()).plus(1)
                lastVisibleItemIndex > (totalItems - threshold)
            }
        }
    } else {
        remember {
            derivedStateOf {
                val layoutInfo =
                    listState.layoutInfo
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex =
                    (layoutInfo.visibleItemsInfo.lastOrNull()?.index.orZero()).plus(1)
                lastVisibleItemIndex > (totalItems - threshold)
            }
        }
    }

    LaunchedEffect(key1 = isLastItemVisible, key2 = uiState.isLastPage) {
        if (isLastItemVisible && uiState.canLoadNextPage()) {
            onEvent(Event.LoadNextPage)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        CustomLazyList(
            data = uiState.pokemonPage,
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
                            top = 100.dp,
                            bottom = PokedexZ1Theme.dimen.medium
                        ),
                    text = stringResource(id = R.string.label_select_pokemon),

                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            },
            key = { _, pokemon -> pokemon.name },
            contentPadding = PaddingValues(PokedexZ1Theme.dimen.medium),
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
                    isShowGridList = isShowGridList,
                    imageModifier = imageModifier,
                    pokemon = item,
                    pokemonClickedList = uiState.pokemonClickedList,
                    onPokemonClick = { clickedPokemon ->
                        onPokemonClick(clickedPokemon)
                    }
                )
            },
            isLastPage = uiState.isLastPage,
            isLoadingPage = uiState.isLoadingPage,
            loadingContent = {
                CustomLoading(
                    iconSize = 100.dp,
                    animateIcon = true
                )
            },
            footerContent = {
                CustomLoading(
                    iconSize = 100.dp,
                    animateIcon = uiState.isConnected.not() && uiState.isLastPage,
                    animateVelocity = 5_000,
                    loadingMessage =
                    if (uiState.isConnected && uiState.isLastPage) R.string.label_saw_all_pokemon
                    else R.string.label_connection_lost
                )
            }
        )
        CustomTopAppBar(
            navigationIcon = {
                CustomIconButton(
                    onIconButtonClick = onMenuNavigationClick,
                    iconImageVector = Icons.Rounded.Menu,
                    iconTint = MaterialTheme.colorScheme.onBackground
                )
            },
            title = {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            actions = {
                CustomIconButton(
                    onIconButtonClick = {

                    },
                    iconImageVector = Icons.Rounded.Search,
                    iconTint = MaterialTheme.colorScheme.onBackground
                )

                CustomIconButton(
                    onIconButtonClick = {
                        onLayoutListChange(!isShowGridList)
                    },
                    iconImageVector =
                    if (isShowGridList.not()) Icons.Outlined.GridView
                    else Icons.Outlined.ViewDay,
                    iconTint = MaterialTheme.colorScheme.onBackground
                )
            }
        )
    }
}



@Composable
private fun PokemonItem(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    pokemon: Pokemon,
    pokemonClickedList: Set<String>,
    isShowGridList: Boolean,
    onPokemonClick: (pokemon: Pokemon) -> Unit
) {
    val dimen =
        if (isShowGridList) PokedexZ1Theme.gridList
        else PokedexZ1Theme.verticalList

    val pokemonAlreadyClicked by remember {
        mutableStateOf(pokemonClickedList.contains(pokemon.name))
    }

    var startAnimation by remember { mutableStateOf(false) }
    val scalePokemon by remember { mutableStateOf(Animatable(1.3f)) }
    LaunchedEffect(key1 = startAnimation, key2 = pokemonAlreadyClicked) {
        if (startAnimation && pokemonAlreadyClicked) {
            onPokemonClick(pokemon)
            return@LaunchedEffect
        }
        if (startAnimation) {
            scalePokemon.animateTo(1.7f, animationSpec = tween(durationMillis = 300))
            scalePokemon.animateTo(0f, animationSpec = tween(durationMillis = 500))
            onPokemonClick(pokemon)
        }
    }

    val animationRotation by animateFloatAsState(
        targetValue = if (startAnimation) 360f else 0f,
        animationSpec = tween(500, easing = LinearEasing) ,
        label = "animation-rotation"
    )

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
        CompositionLocalProvider(
            LocalRippleTheme provides CustomRippleTheme(Color.White)
        ) {
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
                    .clickable { startAnimation = true }
            ) {
                Image(
                    modifier = Modifier
                        .size(dimen.imagePlaceHolderSize)
                        .alpha(0.4f)
                        .padding(dimen.normal)
                        .rotate(animationRotation),
                    painter = painterResource(id = R.drawable.pokeball_placeholder),
                    colorFilter = ColorFilter.tint(Color.White),
                    contentDescription = ""
                )
            }
        }

        CustomShineImage(
            modifier = imageModifier
                .size(dimen.canvaSize)
                .constrainAs(canva) {
                    top.linkTo(image.top)
                    bottom.linkTo(image.bottom)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                },
        )

        pokemon.image?.asImageBitmap()?.let {
            ImageWithShadow(
                modifier = imageModifier
                    .requiredWidth(dimen.imageSize)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end, dimen.imageMarginEnd)
                    }
                    .scale(scalePokemon.value),
                imageBitmap = it,
                contentScale = ContentScale.Fit,
                colorFilter =
                if (pokemonAlreadyClicked) null
                else ColorFilter.tint(Color(0, 0, 0, 255)),
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
                }
                .graphicsLayer {
                    if (pokemonAlreadyClicked.not()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val blur = 20f
                            renderEffect = RenderEffect
                                .createBlurEffect(
                                    blur, blur, Shader.TileMode.DECAL
                                )
                                .asComposeRenderEffect()
                        } else {
                            alpha = 0f
                        }
                    }
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
                page = 0,
                "Pikachu",
                "https://pokeapi.co/api/v2/pokemon/1/"
            ),
            isShowGridList = false,
            onPokemonClick = { _ -> },
            pokemonClickedList = emptySet()
        )
    }
}