package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("slot")
    val slot: Int?, // 1
    @SerializedName("type")
    val type: TypeX?
)