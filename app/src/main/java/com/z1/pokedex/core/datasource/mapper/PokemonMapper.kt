package com.z1.pokedex.core.datasource.mapper

import com.z1.pokedex.core.bases.BaseMapper
import com.z1.pokedex.core.network.model.PokemonDTO
import com.z1.pokedex.feature.home.domain.model.Pokemon

class PokemonMapper : BaseMapper<Pokemon, PokemonDTO>() {
    override fun asModel(entity: PokemonDTO) =
        entity.run {
            Pokemon(
                name = name,
                url = url
            )
        }
}