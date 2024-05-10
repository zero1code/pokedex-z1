package com.z1.pokedex.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_pokemon")
data class PokemonEntity(
    var page: Int = 0,
    @PrimaryKey
    val name: String,
    val url: String
)
