package com.z1.pokedex.core.network.model

import com.squareup.moshi.Json

data class PokemonPageDto(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "next") val nextPage: String?,
    @field:Json(name = "previous") val previousPage: String?,
    @field:Json(name = "results") val results: List<PokemonDTO>
)