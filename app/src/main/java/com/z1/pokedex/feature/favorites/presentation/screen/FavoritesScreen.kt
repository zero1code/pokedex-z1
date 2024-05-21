package com.z1.pokedex.feature.favorites.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.z1.pokedex.R
import com.z1.pokedex.core.common.designsystem.components.CustomLoading
import com.z1.pokedex.core.common.designsystem.components.CustomTopAppBar
import com.z1.pokedex.core.common.designsystem.components.PokemonItem
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.feature.details.presentation.PokemonDetailsContainer
import com.z1.pokedex.feature.favorites.presentation.screen.viewmodel.FavoritesScreenEvent
import com.z1.pokedex.feature.home.domain.model.Pokemon

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favoritesScreenUiState: FavoritesScreenUiState,
    onEvent: (FavoritesScreenEvent) -> Unit,
    onNavigationIconClick: () -> Unit
) {
    var pokemon: Pokemon? by remember { mutableStateOf(null) }
    var detailsIsOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = favoritesScreenUiState.userData) {
        favoritesScreenUiState.userData?.let {
            onEvent(FavoritesScreenEvent.GetFavorites(it.userId))
        }
    }
    AnimatedContent(
        targetState = pokemon,
        transitionSpec = {
            if (targetState != null) {
                slideInHorizontally(
                    tween(easing = FastOutLinearInEasing)
                ) { it } togetherWith
                        ExitTransition.KeepUntilTransitionsFinished
            } else {
                slideInHorizontally { -it } togetherWith (slideOutHorizontally { it / 3 } + fadeOut())
            }
        },
        label = "favorites"
    ) { pokemonState ->
        if (pokemonState != null) {
            detailsIsOpen = true
            PokemonDetailsContainer(
                pokemon = pokemonState,
                onNavigationIconClick = {
                    pokemon = null
                }
            )
        } else {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                FavoritesList(
                    favorites = favoritesScreenUiState.favorites,
                    onPokemonClick = { pokemonClicked ->
                        pokemon = pokemonClicked
                    }
                )
                Header(
                    onNavigationIconClick = {
                        onNavigationIconClick()
                        detailsIsOpen = false
                    }
                )
            }

            AnimatedVisibility(
                visible = favoritesScreenUiState.isLoading.not() && favoritesScreenUiState.favorites.isEmpty() && pokemon == null,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                CustomLoading(
                    modifier = Modifier.fillMaxSize(),
                    iconSize = 100.dp,
                    animateIcon = true,
                    animateVelocity = 5_000,
                    loadingMessage = R.string.label_no_favorite_found
                )
            }
        }
    }

    AnimatedVisibility(
        visible = favoritesScreenUiState.isLoading,
        enter = fadeIn(),
        exit = slideOutVertically(
            animationSpec = tween(easing = LinearEasing)
        ) { it }
    ) {
        CustomLoading(
            modifier = Modifier.fillMaxSize(),
            iconSize = PokedexZ1Theme.dimen.loadingIcon,
            animateIcon = true,
            loadingMessage = R.string.label_loading_pokemon
        )
    }

    BackHandler(pokemon != null) {
        if (pokemon != null) {
            pokemon = null
            detailsIsOpen = false
        }
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
                text = stringResource(id = R.string.label_favorites),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    )
}

@Composable
private fun FavoritesList(
    modifier: Modifier = Modifier,
    favorites: List<Pokemon>,
    onPokemonClick: (Pokemon) -> Unit
) {
    val pokemonClickedList = favorites.map { it.name }.toSet()

    val listState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(PokedexZ1Theme.dimen.medium),
        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    ) {
        item {
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
        }

        itemsIndexed(
            items = favorites,
            key = { _, favorite -> favorite.name }
        ) { _, item ->
            PokemonItem(
                listState = listState,
                pokemon = item,
                pokemonClickedList = pokemonClickedList,
                isShowGridList = false,
                onPokemonClick = { pokemon ->
                    onPokemonClick(pokemon)
                }
            )
        }
    }
}