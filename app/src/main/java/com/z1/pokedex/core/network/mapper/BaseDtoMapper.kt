package com.z1.pokedex.core.network.mapper

interface BaseDtoMapper<Model, DTO> {
    fun asModel(dto: DTO): Model
}