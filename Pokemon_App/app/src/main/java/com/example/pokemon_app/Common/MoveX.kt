package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class MoveX(
    @SerializedName("name")
    val name: String?, // razor-wind
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/move/13/
)