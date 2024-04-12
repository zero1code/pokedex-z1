package com.z1.pokedex.feature.home.domain.mapper

import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import com.z1.pokedex.feature.home.domain.model.PokemonDetails

class PokemonTypeMapper : BaseMapper<PokemonDetails.Type, PokemonDetailsDTO.TypeResponse>() {
    override fun asModel(entity: PokemonDetailsDTO.TypeResponse) =
        entity.run {
            PokemonDetails.Type(
                name = type.name,
                slot = slot
            )
        }
}