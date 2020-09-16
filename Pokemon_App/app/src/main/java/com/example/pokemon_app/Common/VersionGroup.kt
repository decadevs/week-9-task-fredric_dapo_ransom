package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class VersionGroup(
    @SerializedName("name")
    val name: String?, // gold-silver
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/version-group/3/
)