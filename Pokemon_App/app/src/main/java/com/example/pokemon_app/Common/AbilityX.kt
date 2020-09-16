package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class AbilityX(
    @SerializedName("name")
    val name: String?, // overgrow
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/ability/65/
)