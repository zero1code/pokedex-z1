package com.z1.pokedex.core.database.mapper

import com.z1.pokedex.core.database.model.PokemonFavoriteEntity
import com.z1.pokedex.feature.home.domain.model.Pokemon

object PokemonFavoriteEntityMapper : BaseEntityMapper<List<Pokemon>, List<PokemonFavoriteEntity>> {
    override fun asEntity(model: List<Pokemon>) =
        model.map {
            PokemonFavoriteEntity(
                userId = "",
                name = it.name,
                url = it.url
            )
        }

    override fun asModel(entity: List<PokemonFavoriteEntity>) =
        entity.map {
            Pokemon(
                page = 0,
                name = it.name,
                url = it.url
            )
        }
}
fun List<PokemonFavoriteEntity>?.asModel(): List<Pokemon> = PokemonFavoriteEntityMapper.asModel(this.orEmpty())

fun Pokemon.asFavoriteEntity(userId: String) = this.run {
    PokemonFavoriteEntity(
        name = name,
        userId = userId,
        url = url
    )
}