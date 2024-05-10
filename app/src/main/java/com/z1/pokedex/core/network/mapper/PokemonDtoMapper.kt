package com.z1.pokedex.core.network.mapper

import com.z1.pokedex.core.network.model.PokemonDTO
import com.z1.pokedex.feature.home.domain.model.Pokemon

object PokemonDtoMapper : BaseDtoMapper<List<Pokemon>, List<PokemonDTO>> {

    override fun asModel(dto: List<PokemonDTO>) =
        dto.map {
            Pokemon(
                name = it.name,
                url = it.url
            )
        }
}

fun List<PokemonDTO>.asModel() = PokemonDtoMapper.asModel(this)
