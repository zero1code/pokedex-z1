package com.z1.pokedex.feature.home.domain.mapper

import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.core.network.model.PokemonPageDto
import com.z1.pokedex.feature.home.domain.model.PokemonDetails
import com.z1.pokedex.feature.home.domain.model.PokemonPage

class PokemonDetailsMapper(
    private val pokemonTypeMapper: PokemonTypeMapper
) : BaseMapper<PokemonDetails, PokemonDetailsDTO>() {

    override fun asModel(entity: PokemonDetailsDTO) =
        entity.run {
            PokemonDetails(
                id,
                name,
                height,
                weight,
                experience,
                pokemonTypeMapper.asModelList(types)
            )
        }
}