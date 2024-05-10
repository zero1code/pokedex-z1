package com.z1.pokedex.core.database.mapper

import com.z1.pokedex.core.database.model.PokemonEntity
import com.z1.pokedex.feature.home.domain.model.Pokemon

object PokemonBaseEntityMapper : BaseEntityMapper<List<Pokemon>, List<PokemonEntity>> {
    override fun asEntity(model: List<Pokemon>) =
        model.map {
            PokemonEntity(
                page = it.page,
                name = it.name,
                url = it.url
            )
        }

    override fun asModel(entity: List<PokemonEntity>) =
        entity.map {
            Pokemon(
                page = it.page,
                name = it.name,
                url = it.url
            )
        }
}

fun List<Pokemon>.asEntity(): List<PokemonEntity> = PokemonBaseEntityMapper.asEntity(this)
fun List<PokemonEntity>?.asModel(): List<Pokemon> = PokemonBaseEntityMapper.asModel(this.orEmpty())