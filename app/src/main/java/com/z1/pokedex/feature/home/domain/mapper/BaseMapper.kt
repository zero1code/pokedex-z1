package com.z1.pokedex.feature.home.domain.mapper

abstract class BaseMapper<Model, Entity> {
    abstract fun asModel(entity: Entity): Model
    fun asModelList(entityList: List<Entity>) = entityList.map { asModel(it) }
}