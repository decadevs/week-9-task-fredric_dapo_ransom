package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Animated(
    @SerializedName("back_default")
    val backDefault: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/back/1.gif
    @SerializedName("back_female")
    val backFemale: Any?, // null
    @SerializedName("back_shiny")
    val backShiny: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/back/shiny/1.gif
    @SerializedName("back_shiny_female")
    val backShinyFemale: Any?, // null
    @SerializedName("front_default")
    val frontDefault: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/1.gif
    @SerializedName("front_female")
    val frontFemale: Any?, // null
    @SerializedName("front_shiny")
    val frontShiny: String?, // https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/shiny/1.gif
    @SerializedName("front_shiny_female")
    val frontShinyFemale: Any? // null
)