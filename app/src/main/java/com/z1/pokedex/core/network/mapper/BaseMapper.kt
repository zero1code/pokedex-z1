package com.z1.pokedex.core.network.mapper

abstract class BaseMapper<Model, DTO> {
    abstract fun mapDtoToModel(dto: DTO): Model
    abstract fun mapModelToDto(model: Model): DTO

    fun mapModelListToDtoList(modelList: List<Model>) = modelList.map { mapModelToDto(it) }
    fun mapDtoListToModelList(dtoList: List<DTO>) = dtoList.map { mapDtoToModel(it) }
}