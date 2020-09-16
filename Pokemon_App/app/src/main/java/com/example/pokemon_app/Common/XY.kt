package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class XY(
    @SerializedName("front_default")
    val frontDefault: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vi/x-y/1.png
    @SerializedName("front_female")
    val frontFemale: Any?, // null
    @SerializedName("front_shiny")
    val frontShiny: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vi/x-y/shiny/1.png
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? // null
)