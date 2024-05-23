package com.z1.pokedex.feature.subscription.presentation.screen

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.z1.pokedex.R
import com.z1.pokedex.core.common.designsystem.components.CustomTopAppBar
import com.z1.pokedex.core.common.designsystem.theme.MediumSeaGreen
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataState
import com.z1.pokedex.feature.subscription.presentation.screen.viewmodel.SubscriptionScreenEvent

@Composable
fun SubscriptionScreen(
    modifier: Modifier = Modifier,
    userData: UserDataState,
    uiState: SubscriptionScreenUiState,
    onEvent: (SubscriptionScreenEvent) -> Unit,
    onNavigationIconClick: () -> Unit
) {

    AnimatedVisibility(
        visible = userData.isPremium(),
        enter = scaleIn(),
        exit = fadeOut()
    ) {
        AlreadyPremium(
            modifier = modifier.fillMaxSize(),
            uiState.userClickedInSubscriptionButton
        )
    }

    AnimatedVisibility(
        visible = userData.isPremium().not(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Header(onNavigationIconClick = onNavigationIconClick)
            SubscriptionBenefits(
                isShowSubscriptionButton = userData.data != null,
                onSubscriptionClick = {
                    onEvent(
                        SubscriptionScreenEvent.Subscribe(userData.data?.userId ?: "")
                    )
                }
            )
        }
    }

    BackHandler(true) {
        onNavigationIconClick()
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
                text = stringResource(id = R.string.label_subscription),
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
private fun SubscriptionBenefits(
    modifier: Modifier = Modifier,
    isShowSubscriptionButton: Boolean,
    onSubscriptionClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (title, listBenefits, subscriptionPrice, button) = createRefs()

        Text(
            modifier = Modifier
                .padding(PokedexZ1Theme.dimen.medium)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            text = stringResource(id = R.string.label_exclusive_access),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )

        Column(
            modifier = Modifier
                .padding(
                    horizontal = PokedexZ1Theme.dimen.medium
                )
                .constrainAs(listBenefits) {
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom)
                }
        ) {
            Benefit(title = R.string.label_subscription_benefit_1)
            Benefit(title = R.string.label_subscription_benefit_2)
            Benefit(title = R.string.label_subscription_benefit_3)
            Benefit(title = R.string.label_subscription_benefit_4)
        }

        SubscriptionPrice(
            modifier = Modifier
                .constrainAs(subscriptionPrice) {
                    start.linkTo(parent.start)
                    top.linkTo(listBenefits.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                }
        )

        AnimatedVisibility(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            visible = isShowSubscriptionButton,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        PokedexZ1Theme.dimen.medium
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MediumSeaGreen
                ),
                onClick = onSubscriptionClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.WorkspacePremium,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier
                        .padding(start = PokedexZ1Theme.dimen.medium),
                    text = stringResource(
                        id = R.string.label_subscribe_now
                    )
                )
            }
        }
    }
}

@Composable
fun Benefit(
    modifier: Modifier = Modifier,
    @StringRes title: Int
) {
    Row(
        modifier = modifier
            .padding(
                top = PokedexZ1Theme.dimen.medium
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = null,
            tint = MediumSeaGreen
        )
        Spacer(modifier = Modifier.width(PokedexZ1Theme.dimen.medium))
        Text(text = stringResource(id = title))
    }
}

@Composable
fun SubscriptionPrice(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.label_subscribe_per),
            style = MaterialTheme.typography.titleSmall
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.label_dolar),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "1.00",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = R.string.label_period),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Text(
            text = stringResource(id = R.string.label_test_period),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
private fun AlreadyPremium(
    modifier: Modifier = Modifier,
    userClickedInSubscriptionButton: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite-transition")
    val animationRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5_000, easing = LinearEasing),
        ),
        label = "animation-rotation"
    )

    val backGrounColor = if (userClickedInSubscriptionButton) MediumSeaGreen
    else MaterialTheme.colorScheme.background

    Column(
        modifier = modifier
            .drawBehind {
                drawRoundRect(color = backGrounColor)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .rotate(animationRotation),
            painter = painterResource(id = R.drawable.pokeball_placeholder),
            colorFilter =
            if (userClickedInSubscriptionButton) ColorFilter.tint(Color.White)
            else ColorFilter.tint(MediumSeaGreen),
            contentDescription = null
        )
        Spacer(
            modifier = Modifier
                .height(PokedexZ1Theme.dimen.medium)
        )
        Text(
            text = stringResource(
                id =
                if (userClickedInSubscriptionButton) R.string.label_premium_now
                else R.string.label_already_premium
            ),
            color =
            if (userClickedInSubscriptionButton) Color.White
            else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SubscriptionScreenPreview() {
    PokedexZ1Theme {
        SubscriptionScreen(
            userData = UserDataState(),
            uiState = SubscriptionScreenUiState(),
            onEvent = {},
            onNavigationIconClick = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun AlreadyPremiumScreenPreview() {
    PokedexZ1Theme {
        AlreadyPremium(
            userClickedInSubscriptionButton = true
        )
    }
}