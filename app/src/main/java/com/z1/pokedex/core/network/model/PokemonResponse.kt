package com.z1.pokedex.core.network.model

import com.squareup.moshi.Json

data class PokemonResponse(
    @field:Json(name = "count") val count: Int,
    @field:Json(name = "next") val next: String?,
    @field:Json(name = "previous") val previous: String?,
    @field:Json(name = "results") val results: List<PokemonDTO>
)
