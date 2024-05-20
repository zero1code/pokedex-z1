package com.z1.pokedex.feature.home.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.z1.pokedex.R
import com.z1.pokedex.core.network.model.google.UserData
import com.z1.pokedex.designsystem.theme.CoralRed
import com.z1.pokedex.designsystem.theme.Glacier
import com.z1.pokedex.designsystem.theme.PokedexZ1Theme

enum class DrawerRoute(val route: String) {
    HOME("home"),
    FAVORITES("favorites"),
    SUBSCRIPTION("subscription")
}

@Immutable
data class DrawerMenuItem(
    val icon: ImageVector,
    @StringRes val title: Int,
    val route: String
)

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    userData: UserData?,
    onNavigationItemClick: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    val navigationItems = remember {
        listOf(
            DrawerMenuItem(Icons.Rounded.Home, R.string.label_home, DrawerRoute.HOME.route),
            DrawerMenuItem(Icons.Rounded.FavoriteBorder, R.string.label_favorites, DrawerRoute.FAVORITES.route),
            DrawerMenuItem(Icons.Rounded.WorkspacePremium, R.string.label_become_pro, DrawerRoute.SUBSCRIPTION.route)
        )
    }

    ModalDrawerSheet(
        modifier = modifier
            .requiredWidth(300.dp)
            .navigationBarsPadding(),
        drawerShape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = PokedexZ1Theme.dimen.large,
            bottomEnd = PokedexZ1Theme.dimen.large,
        ),
        drawerContainerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                DrawerHeader(userData = userData)
                Spacer(modifier = Modifier.height(PokedexZ1Theme.dimen.medium))
                DrawerNavigationItems(
                    navigationItems = navigationItems,
                    onNavigationItemClick = { route ->
                        onNavigationItemClick(route)
                    }
                )
            }
            DrawerFooter(
                onLogoutClick = onLogoutClick
            )
        }
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier,
    userData: UserData?
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val (bgImage, textUsername, imageUser) = createRefs()

        Image(
            modifier = Modifier
                .constrainAs(bgImage) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.FillWidth,
            painter = painterResource(id = R.drawable.bg_pokemon_login),
            contentDescription = null,
            alpha = 0.2f
        )
        userData?.let {
            AsyncImage(
                modifier = Modifier
                    .padding(start = PokedexZ1Theme.dimen.medium)
                    .constrainAs(imageUser) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(PokedexZ1Theme.dimen.large2)
                    .clip(RoundedCornerShape(PokedexZ1Theme.dimen.large)),
                model = it.profilePictureUrl,
                contentDescription = null
            )
            it.userName?.let { userName ->
                Text(
                    modifier = Modifier
                        .padding(
                            start = PokedexZ1Theme.dimen.medium,
                            top = PokedexZ1Theme.dimen.medium,
                        )
                        .constrainAs(textUsername) {
                            start.linkTo(parent.start)
                            top.linkTo(imageUser.bottom)
                        },
                    text = userName,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun DrawerNavigationItems(
    modifier: Modifier = Modifier,
    navigationItems: List<DrawerMenuItem>,
    onNavigationItemClick: (String) -> Unit
) {
    var selectedItem by remember {
        mutableStateOf(navigationItems[0])
    }
    navigationItems.forEach { item ->
        NavigationDrawerItem(
            modifier = modifier
                .padding(end = PokedexZ1Theme.dimen.medium),
            label = { Text(text = stringResource(id = item.title)) },
            icon = { Icon(imageVector = item.icon, contentDescription = null) },
            selected = selectedItem == item,
            onClick = {
                selectedItem = item
                onNavigationItemClick(item.route)
            },
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = PokedexZ1Theme.dimen.large,
                bottomEnd = PokedexZ1Theme.dimen.large
            ),
            colors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Glacier.copy(alpha = 0.2f)
            )
        )
    }
}

@Composable
private fun DrawerFooter(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(PokedexZ1Theme.dimen.large2)
            .clickable { onLogoutClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.Logout,
            contentDescription = null,
            tint = CoralRed
        )
        Spacer(modifier = Modifier.width(PokedexZ1Theme.dimen.normal))
        Text(
            text = stringResource(id = R.string.label_logout),
            color = CoralRed
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DrawerContentPreview() {
    PokedexZ1Theme {
        DrawerContent(
            userData = UserData(
                "",
                "Airton Oliveira",
                ""
            ),
            onNavigationItemClick = {},
            onLogoutClick = {}
        )
    }
}