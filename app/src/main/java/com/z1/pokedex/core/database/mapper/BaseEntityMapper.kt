package com.z1.pokedex.core.database.mapper

interface BaseEntityMapper<Model, Entity> {
    fun asEntity(model: Model): Entity
    fun asModel(entity: Entity): Model
}