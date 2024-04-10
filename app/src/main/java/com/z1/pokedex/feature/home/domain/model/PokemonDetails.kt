package com.z1.pokedex.feature.home.domain.model

import com.z1.pokedex.core.network.model.PokemonDetailsDTO
import kotlin.random.Random

data class PokemonDetails(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
    val types: List<PokemonDetailsDTO.TypeResponse> = emptyList(),
    val hp: Int = Random.nextInt(MAX_HP),
    val attack: Int = Random.nextInt(MAX_ATTACK),
    val defense: Int = Random.nextInt(MAX_DEFENSE),
    val speed: Int = Random.nextInt(MAX_SPEED),
    val exp: Int = Random.nextInt(MAX_EXP),
) {
    fun getWeightString(): String = String.format("%.1f KG", weight.toFloat() / 10)
    fun getHeightString(): String = String.format("%.1f M", height.toFloat() / 10)
    fun getHpString(): String = " $hp/$MAX_HP"
    fun getAttackString(): String = " $attack/$MAX_ATTACK"
    fun getDefenseString(): String = " $defense/$MAX_DEFENSE"
    fun getSpeedString(): String = " $speed/$MAX_SPEED"
    fun getExpString(): String = " $exp/$MAX_EXP"

    companion object {
        const val MAX_HP = 300
        const val MAX_ATTACK = 300
        const val MAX_DEFENSE = 300
        const val MAX_SPEED = 300
        const val MAX_EXP = 1000
    }
}
