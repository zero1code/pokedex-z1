package com.z1.pokedex.feature.details.domain.repository.mapper

import com.z1.pokedex.core.network.mapper.BaseDtoMapper
import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.feature.details.domain.model.PokemonDetails

object PokemonTypesDtoMapper :
    BaseDtoMapper<List<PokemonDetails.Type>, List<PokemonDetailsDTO.TypeResponse>> {
    override fun asModel(dto: List<PokemonDetailsDTO.TypeResponse>) =
        dto.map {
            PokemonDetails.Type(
                slot = it.slot,
                name = it.type.name
            )
        }
}

fun List<PokemonDetailsDTO.TypeResponse>.asModel() = PokemonTypesDtoMapper.asModel(this)