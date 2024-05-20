package com.z1.pokedex.core.network.mapper

import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.feature.home.domain.model.PokemonDetails

object PokemonDetailsDtoMapper : BaseDtoMapper<PokemonDetails, PokemonDetailsDTO> {
    override fun asModel(dto: PokemonDetailsDTO) =
        dto.run {
            PokemonDetails(
                id,
                name,
                height,
                weight,
                experience,
                types.asModel()
            )
        }
}

fun PokemonDetailsDTO.asModel() = PokemonDetailsDtoMapper.asModel(this)