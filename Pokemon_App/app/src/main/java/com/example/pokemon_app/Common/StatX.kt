package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class StatX(
    @SerializedName("name")
    val name: String?, // hp
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/stat/1/
)