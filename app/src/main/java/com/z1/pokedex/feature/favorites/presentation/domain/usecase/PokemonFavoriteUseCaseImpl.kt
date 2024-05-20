package com.z1.pokedex.feature.favorites.presentation.domain.usecase

import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.z1.pokedex.core.database.repository.favorites.PokemonFavoriteRepository
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonFavoriteUseCaseImpl(
    private val pokemonFavoriteRepository: PokemonFavoriteRepository
): PokemonFavoriteUseCase {
    override suspend fun getPokemonFavorites(userId: String): Flow<List<Pokemon>> {
        return pokemonFavoriteRepository.getPokemonFavorites(userId).map { newPage ->
            val updatedPokemonList = newPage.map { pokemon ->
                val updatedPokemon = pokemonFavoriteRepository.fetchPokemonImage(pokemon.getImageUrl())?.let {
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