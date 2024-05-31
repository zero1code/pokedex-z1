package com.z1.pokedex.feature.home.presentation.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.printToLog
import com.z1.pokedex.R
import com.z1.pokedex.core.common.designsystem.components.GRID_LAZY_LIST_TEST_TAG
import com.z1.pokedex.core.common.designsystem.components.MODAL_DRAWER_TEST_TAG
import com.z1.pokedex.core.common.designsystem.components.VERTICAL_LAZY_LIST_TEST_TAG
import com.z1.pokedex.core.common.designsystem.theme.PokedexZ1Theme
import com.z1.pokedex.core.common.shared.viewmodel.userdata.UserDataState
import com.z1.pokedex.core.testing.fakeUser
import com.z1.pokedex.core.testing.pokemonPage1DataTest
import org.junit.Rule
import org.junit.Test
class HomeScreenKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var fakeUiState: HomeScreenUiState
    private lateinit var fakeUserData: UserDataState

    private fun init() {
        composeTestRule.setContent {
            PokedexZ1Theme {
                HomeScreen(
                    userData = fakeUserData,
                    uiState = fakeUiState,
                    onEvent = {},
                    onLogoutClick = {},
                    navigateToSubscriptionScreen = {},
                    drawerNavigation = {}
                )
            }
        }
    }

    @Test
    fun should_Show_First_Login_When_Application_Start_With_Signed_In_User() {
        // Given - Dado
        val loadingPokemonMessage = composeTestRule.activity.getString(R.string.label_loading_pokemon)
        fakeUiState = HomeScreenUiState()
        fakeUserData = UserDataState(data = fakeUser)

        // When - Quando
        init()

        // Then - Entao
        composeTestRule.onNodeWithText(loadingPokemonMessage).assertIsDisplayed()
    }

    @Test
    fun should_Show_Pokemon_List_When_Data_Is_Available() {
        // Given - Dado
        val appNameText = composeTestRule.activity.getString(R.string.app_name)
        val pokemonNumber = "#001"
        fakeUiState = HomeScreenUiState(
            pokemonPage = pokemonPage1DataTest,
            isFirstLoading = false,
            isLoadingPage = false,
            isConnected = true
        )
        fakeUserData = UserDataState(data = fakeUser)

        // When - Quando
        init()

        // Then - Entao
        composeTestRule.onNodeWithText(appNameText).assertIsDisplayed()
        composeTestRule.onNodeWithText(pokemonNumber).assertIsDisplayed()
    }

    @Test
    fun should_Show_Details_Screen_When_Click_In_A_Pokemon() {
        // Given - Dado
        val detailsText = composeTestRule.activity.getString(R.string.label_pokemon_details)
        val becomeProMessage = composeTestRule.activity.getString(R.string.label_premium_statistics)
        val pokemonNumber = "#001"
        fakeUiState = HomeScreenUiState(
            pokemonPage = pokemonPage1DataTest,
            lastPokemonClicked = pokemonPage1DataTest[0],
            isFirstLoading = false,
            isLoadingPage = false,
            isConnected = true
        )
        fakeUserData = UserDataState(data = fakeUser)
        init()

        // When - Quando
        composeTestRule.onNodeWithText(pokemonNumber).performClick()

        // Then - Entao
        composeTestRule.onNodeWithText(detailsText).assertIsDisplayed()
        composeTestRule.onNodeWithText(becomeProMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText(pokemonNumber).assertIsDisplayed()
    }

    @Test
    fun should_Show_Loading_When_Scroll_List_To_End() {
        // Given - Dado
        val loadingMorePokemonMessage = composeTestRule.activity.getString(R.string.label_loading_more_pokemon)
        fakeUiState = HomeScreenUiState(
            pokemonPage = pokemonPage1DataTest,
            isFirstLoading = false,
            isLoadingPage = true,
            isConnected = true
        )
        fakeUserData = UserDataState(data = fakeUser)
        init()

        // When - Quando
        composeTestRule.onNodeWithTag(VERTICAL_LAZY_LIST_TEST_TAG).performScrollToIndex(19)

        // Then - Entao
        composeTestRule.onNodeWithText(loadingMorePokemonMessage).assertIsDisplayed()
    }

    @Test
    fun should_Show_Grid_List_When_Click_In_Button_To_Change() {
        // Given - Dado
        val pokemonNumber = "#008"
        fakeUiState = HomeScreenUiState(
            pokemonPage = pokemonPage1DataTest,
            isFirstLoading = false,
            isLoadingPage = true,
            isConnected = true
        )
        fakeUserData = UserDataState(data = fakeUser)
        init()

        // When - Quando
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("currentTree")
        composeTestRule.onNodeWithTag(GRID_LAZY_LIST_TEST_TAG).performClick()

        // Then - Entao
        composeTestRule.onNodeWithText(pokemonNumber).assertIsDisplayed()
    }

    @Test
    fun should_Show_Navigation_Drawer_When_Click_In_Button_To_Open_It() {
        // Given - Dado
        val homeText = composeTestRule.activity.getString(R.string.label_home)
        fakeUiState = HomeScreenUiState(
            pokemonPage = pokemonPage1DataTest,
            isFirstLoading = false,
            isLoadingPage = true,
            isConnected = true
        )
        fakeUserData = UserDataState(data = fakeUser)
        init()

        // When - Quando
        composeTestRule.onNodeWithTag(MODAL_DRAWER_TEST_TAG).performClick()

        // Then - Entao
        composeTestRule.onNodeWithText(homeText).assertIsDisplayed()
    }
}