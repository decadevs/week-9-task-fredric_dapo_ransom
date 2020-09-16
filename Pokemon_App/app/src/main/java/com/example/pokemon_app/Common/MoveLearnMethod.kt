package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class MoveLearnMethod(
    @SerializedName("name")
    val name: String?, // egg
    @SerializedName("url")
    val url: String? // https://pokeapi.co/api/v2/move-learn-method/2/
)