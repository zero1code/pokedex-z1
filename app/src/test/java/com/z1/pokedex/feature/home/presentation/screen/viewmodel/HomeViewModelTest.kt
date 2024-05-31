@file:OptIn(ExperimentalCoroutinesApi::class)

package com.z1.pokedex.feature.home.presentation.screen.viewmodel

import com.z1.pokedex.BaseTest
import com.z1.pokedex.core.common.shared.services.connectivity.ConnectivityService
import com.z1.pokedex.core.testing.pokemonPage1DataTest
import com.z1.pokedex.core.testing.pokemonPage2DataTest
import com.z1.pokedex.feature.home.domain.usecase.PokemonUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeViewModelTest : BaseTest() {
    private lateinit var homeViewModel: HomeViewModel
    private var isConnected = true
    private val pokemonUseCase: PokemonUseCase = mockk()
    private val connectivityService: ConnectivityService = mockk()

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        coEvery { connectivityService.isConnected } answers { flowOf(isConnected) }
        homeViewModel = HomeViewModel(pokemonUseCase, connectivityService)
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `should return empty list when no page saved local and network is offline`() {
        // Given - Dado
        isConnected = false
        beforeEach()
        coEvery { pokemonUseCase.fetchPokemonPage(0) } returns flowOf(emptyList())

        // When - Quando
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        // Then - Entao
        val uiState = homeViewModel.uiState.value
        assertTrue(uiState.pokemonPage.isEmpty())
    }

    @Test
    fun `should return list with only 20 items when have saved data local and network is offline`() {
        // Given - Dado
        isConnected = false
        beforeEach()
        coEvery { pokemonUseCase.fetchPokemonPage(0) } returns flowOf(pokemonPage1DataTest)
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        coEvery { pokemonUseCase.fetchPokemonPage(1) } returns flowOf(emptyList())

        // When - Quando
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        // Then - Entao
        val uiState = homeViewModel.uiState.value
        assertEquals(20, uiState.pokemonPage.size)
    }

    @Test
    fun `should return list with 40 items when have saved data local and network is offline`() {
        // Given - Dado
        coEvery { pokemonUseCase.fetchPokemonPage(0) } returns flowOf(pokemonPage1DataTest)
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        coEvery { pokemonUseCase.fetchPokemonPage(1) } returns flowOf(pokemonPage2DataTest)

        // When - Quando
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        // Then - Entao
        val uiState = homeViewModel.uiState.value
        assertEquals(40, uiState.pokemonPage.size)
    }

    @Test
    fun `should return list with only 20 items when no have saved data local and network is online`() {
        // Given - Dado
        coEvery { pokemonUseCase.fetchPokemonPage(0) } returns flowOf(pokemonPage1DataTest)

        // When - Quando
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        // Then - Entao
        val uiState = homeViewModel.uiState.value
        assertEquals(20, uiState.pokemonPage.size)
    }

    @Test
    fun `should return list with 40 items when network is online`() {
        // Given - Dado
        coEvery { pokemonUseCase.fetchPokemonPage(0) } returns flowOf(pokemonPage1DataTest)
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        coEvery { pokemonUseCase.fetchPokemonPage(1) } returns flowOf(pokemonPage2DataTest)

        // When - Quando
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        // Then - Entao
        val uiState = homeViewModel.uiState.value
        assertEquals(40, uiState.pokemonPage.size)
    }

    @Test
    fun `should return error when something wrong happen while getting pokemon page`() {
        // Given - Dado
        coEvery { pokemonUseCase.fetchPokemonPage(0) } returns flow { throw Exception() }

        // When - Quando
        homeViewModel.onEvent(HomeScreenEvent.LoadNextPage)

        // Then - Entao
        val uiState = homeViewModel.uiState.value
        assertTrue(uiState.pokemonPage.isEmpty())
        assertFalse(uiState.isLoadingPage)
        assertFalse(uiState.isFirstLoading)
    }

    @Test
    fun `should return last selected pokemon and add his name in pokemonClickedList in the uiState`() =
        runTest {
            // Given - Dado
            val pokemonClicked = pokemonPage1DataTest[0]

            // When - Quando
            homeViewModel.onEvent(HomeScreenEvent.PokemonClicked(pokemonClicked))
            advanceUntilIdle()

            // Then - Entao
            val uiState = homeViewModel.uiState.value
            assertEquals(pokemonClicked.name, uiState.lastPokemonClicked?.name)
            assertTrue(uiState.pokemonClickedList.contains(pokemonClicked.name))
        }
}