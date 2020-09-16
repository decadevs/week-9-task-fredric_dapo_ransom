package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int?, // 45
    @SerializedName("effort")
    val effort: Int?, // 0
    @SerializedName("stat")
    val stat: StatX?
)