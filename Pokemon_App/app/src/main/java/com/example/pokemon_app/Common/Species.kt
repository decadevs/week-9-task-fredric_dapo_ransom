package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Species(
    @SerializedName("name")
    val name: String?, // bulbasaur
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/pokemon-species/1/
)