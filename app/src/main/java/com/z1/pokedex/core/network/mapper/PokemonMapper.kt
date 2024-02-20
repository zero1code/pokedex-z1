package com.z1.pokedex.core.network.mapper

import com.z1.pokedex.core.network.model.PokemonDTO
import com.z1.pokedex.feature.home.presentation.model.Pokemon

class PokemonMapper : BaseMapper<Pokemon, PokemonDTO>() {
    override fun mapDtoToModel(dto: PokemonDTO): Pokemon =
        dto.run {
            Pokemon(
                name = name
            )
        }

    override fun mapModelToDto(model: Pokemon): PokemonDTO =
        model.run {
            PokemonDTO(
                name = name,
                url = ""
            )
        }
}