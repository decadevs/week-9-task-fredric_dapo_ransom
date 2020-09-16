package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class TypeX(
    @SerializedName("name")
    val name: String?, // grass
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/type/12/
)