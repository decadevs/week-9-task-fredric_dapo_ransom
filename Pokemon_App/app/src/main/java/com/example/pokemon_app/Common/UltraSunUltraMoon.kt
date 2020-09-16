package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class UltraSunUltraMoon(
    @SerializedName("front_default")
    val frontDefault: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/ultra-sun-ultra-moon/1.png
    @SerializedName("front_female")
    val frontFemale: Any?, // null
    @SerializedName("front_shiny")
    val frontShiny: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/ultra-sun-ultra-moon/shiny/1.png
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? // null
)