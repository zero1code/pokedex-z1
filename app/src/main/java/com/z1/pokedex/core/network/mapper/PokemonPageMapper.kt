package com.z1.pokedex.core.network.mapper

import com.z1.pokedex.core.network.model.PokemonPageDto
import com.z1.pokedex.feature.home.domain.model.PokemonPage

class PokemonPageMapper(
    private val pokemonMapper: PokemonMapper
): BaseMapper<PokemonPage, PokemonPageDto>() {
    override fun mapDtoToModel(dto: PokemonPageDto): PokemonPage =
        dto.run {
            PokemonPage(
                count = count,
                previousPage = previousPage,
                nextPage = nextPage,
                pokemonList = pokemonMapper.mapDtoListToModelList(results)
            )
        }

    override fun mapModelToDto(model: PokemonPage): PokemonPageDto =
        model.run {
            PokemonPageDto(
                count = count,
                previousPage = previousPage,
                nextPage = nextPage,
                results = pokemonMapper.mapModelListToDtoList(pokemonList)
            )
        }
}