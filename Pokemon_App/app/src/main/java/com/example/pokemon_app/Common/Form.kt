package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Form(
    @SerializedName("name")
    val name: String?, // bulbasaur
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/pokemon-form/1/
)