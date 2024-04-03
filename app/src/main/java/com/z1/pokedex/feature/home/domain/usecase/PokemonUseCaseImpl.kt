package com.z1.pokedex.feature.home.domain.usecase

import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.z1.pokedex.core.datasource.repository.PokemonRepository
import com.z1.pokedex.feature.home.domain.model.PokemonPage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonUseCaseImpl(
    private val pokemonRepository: PokemonRepository
) : PokemonUseCase {
    override suspend fun fetchPokemonPage(page: Int): Flow<PokemonPage> {
        return pokemonRepository.fetchPokemonPage(page).map { newPage ->
            val updatedPokemonList = newPage.pokemonList.map { pokemon ->
                val updatedPokemon = pokemonRepository.fetchPokemonImage(pokemon.getImageUrl())?.let {
                    val bitmap = it.toBitmap().copy(Bitmap.Config.ARGB_8888, false)
                    val palette = Palette.from(bitmap).generate()
                    pokemon.copy(
                        image = bitmap,
                        palette = palette
                    )
                } ?: pokemon // Se a imagem não puder ser carregada, mantenha o pokemon original

                updatedPokemon
            }
            newPage.copy(pokemonList = updatedPokemonList)
        }
    }
}