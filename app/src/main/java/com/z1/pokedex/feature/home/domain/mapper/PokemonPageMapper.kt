package com.z1.pokedex.feature.home.domain.mapper

import com.z1.pokedex.core.network.model.PokemonPageDto
import com.z1.pokedex.feature.home.domain.model.PokemonPage

class PokemonPageMapper(
    private val pokemonMapper: PokemonMapper
) : BaseMapper<PokemonPage, PokemonPageDto>() {
    override fun asModel(entity: PokemonPageDto) =
        entity.run {
            PokemonPage(
                count = count,
                previousPage = previousPage,
                nextPage = nextPage,
                pokemonList = pokemonMapper.asModelList(results)
            )
        }
}