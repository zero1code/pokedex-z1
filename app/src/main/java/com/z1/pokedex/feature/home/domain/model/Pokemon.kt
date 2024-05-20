package com.z1.pokedex.feature.home.domain.model

import android.graphics.Bitmap
import androidx.palette.graphics.Palette

data class Pokemon(
    var page: Int = 0,
    val name: String,
    val url: String,
    val image: Bitmap? = null,
    val palette: Palette? = null
) {
    fun getImageUrl(): String {
        val index = getIndex()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/home/$index.png"
    }

    fun getIndex() = url.split("/".toRegex()).dropLast(1).last().toInt()

    fun dominantColor() = palette?.getDominantColor(16777215) ?: 16777215
//    fun vibrantDarkColor() = palette?.getDarkVibrantColor(palette.getDarkMutedColor(2302755)) ?: 2302755
    fun vibrantDarkColor() =
        when {
            palette?.darkVibrantSwatch != null -> palette.darkVibrantSwatch!!.rgb
            palette?.darkMutedSwatch != null -> palette.darkMutedSwatch!!.rgb
            palette?.dominantSwatch != null -> palette.dominantSwatch!!.rgb
            palette?.vibrantSwatch != null -> palette.vibrantSwatch!!.rgb
            else -> 2302755
    }

}
