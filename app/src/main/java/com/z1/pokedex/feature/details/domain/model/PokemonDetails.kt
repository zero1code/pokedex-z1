package com.z1.pokedex.feature.details.domain.model

import androidx.compose.runtime.Immutable
import kotlin.random.Random

@Immutable
data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
    val types: List<Type> = emptyList(),
    val hp: Float = Random.nextInt(MIN, MAX_HP).toFloat(),
    val attack: Float = Random.nextInt(MIN, MAX_ATTACK).toFloat(),
    val defense: Float = Random.nextInt(MIN, MAX_DEFENSE).toFloat(),
    val speed: Float = Random.nextInt(MIN, MAX_SPEED).toFloat(),
    val exp: Float = Random.nextInt(MIN, MAX_EXP).toFloat(),
) {
    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("%.1f M", height.toFloat() / 10)

    data class Type(
        val slot: Int,
        val name: String
    )

    companion object {
        const val MIN = 100
        const val MAX_HP = 300
        const val MAX_ATTACK = 300
        const val MAX_DEFENSE = 250
        const val MAX_SPEED = 200
        const val MAX_EXP = 500
    }
}