package com.z1.pokedex.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_favorite")
data class PokemonFavoriteEntity(
    @PrimaryKey
    val name: String,
    val url: String,
    @ColumnInfo(name = "user_id")
    val userId: String
)
