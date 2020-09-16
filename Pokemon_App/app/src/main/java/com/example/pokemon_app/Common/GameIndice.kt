package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class GameIndice(
    @SerializedName("game_index")
    val gameIndex: Int?, // 153
    @SerializedName("version")
    val version: Version?
)