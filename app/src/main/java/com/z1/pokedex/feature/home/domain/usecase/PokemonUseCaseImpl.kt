package com.z1.pokedex.feature.home.domain.usecase

import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.z1.pokedex.core.database.repository.favorites.PokemonFavoriteRepository
import com.z1.pokedex.core.datasource.repository.PokemonRepository
import com.z1.pokedex.feature.home.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PokemonUseCaseImpl(
    private val pokemonRepository: PokemonRepository,
    private val pokemonFavoriteRepository: PokemonFavoriteRepository
) : PokemonUseCase {
    override suspend fun fetchPokemonPage(page: Int): Flow<List<Pokemon>> {
        return pokemonRepository.fetchPokemonPage(page).map { newPage ->
            val updatedPokemonList = newPage.map { pokemon ->
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
            updatedPokemonList
        }
    }

    override suspend fun fetchPokemonDetails(pokemonName: String) =
        pokemonRepository.fetchPokemonDetails(pokemonName)

    override suspend fun getPokemonFavoritesName(userId: String) =
        flow {
            pokemonFavoriteRepository.getPokemonFavoritesName(userId).collect {
                emit(it)
            }
        }

    override suspend fun insertPokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonFavoriteRepository.insertPokemonFavorite(pokemon, userId)

    override suspend fun deletePokemonFavorite(pokemon: Pokemon, userId: String) =
        pokemonFavoriteRepository.deletePokemonFavorite(pokemon, userId)
}