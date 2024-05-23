package com.z1.pokedex.feature.home.domain.usecase

import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.z1.pokedex.core.common.shared.domain.repository.PokemonImageRepository
import com.z1.pokedex.feature.home.domain.model.Pokemon
import com.z1.pokedex.feature.home.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonUseCaseImpl(
    private val pokemonRepository: PokemonRepository,
    private val pokemonImageRepository: PokemonImageRepository
) : PokemonUseCase {
    override suspend fun fetchPokemonPage(page: Int): Flow<List<Pokemon>> {
        return pokemonRepository.fetchPokemonPage(page).map { newPage ->
            val updatedPokemonList = newPage.map { pokemon ->
                val updatedPokemon = pokemonImageRepository.fetchPokemonImage(pokemon.getImageUrl())?.let {
                    val bitmap = it.toBitmap().copy(Bitmap.Config.ARGB_8888, false)
                    val palette = Palette.from(bitmap).generate()
                    pokemon.copy(
                        image = bitmap,
                        palette = palette
                    )
                } ?: pokemon // Se a imagem não puder ser carregada, mantenha o pokemon original
                updatedPokemon
            }
            updatedPokemonList
        }
    }
}