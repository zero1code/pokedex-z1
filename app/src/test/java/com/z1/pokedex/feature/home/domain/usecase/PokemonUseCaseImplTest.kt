package com.z1.pokedex.feature.home.domain.usecase

import com.z1.pokedex.BaseTest
import com.z1.pokedex.core.common.shared.domain.repository.PokemonImageRepository
import com.z1.pokedex.core.testing.pokemonPage1DataTest
import com.z1.pokedex.feature.home.domain.repository.PokemonRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PokemonUseCaseImplTest : BaseTest() {
    private lateinit var pokemonUseCase: PokemonUseCase
    private val pokemonRepository: PokemonRepository = mockk()
    private val pokemonImageRepository: PokemonImageRepository = mockk()

    @BeforeEach
    override fun beforeEach() {
        super.beforeEach()
        pokemonUseCase = PokemonUseCaseImpl(pokemonRepository, pokemonImageRepository)
    }

    @AfterEach
    override fun afterEach() {
        super.afterEach()
        clearAllMocks()
    }

    @Test
    fun `should return a pokemon page`() = runTest {
        // Given - Dado
        coEvery { pokemonRepository.fetchPokemonPage(0) } returns flowOf(pokemonPage1DataTest)
        coEvery { pokemonImageRepository.fetchPokemonImage(any()) } answers { null }

        // When - Quando
        val pokemonPage = pokemonUseCase.fetchPokemonPage(0).first()

        // Then - Entao
        assertEquals(20, pokemonPage.size)
    }
}