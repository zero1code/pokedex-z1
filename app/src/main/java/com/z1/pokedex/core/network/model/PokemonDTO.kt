package com.z1.pokedex.core.network.model

import com.squareup.moshi.Json

data class PokemonDTO(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "url") val url: String
)