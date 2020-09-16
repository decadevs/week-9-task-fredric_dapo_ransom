package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability")
    val ability: AbilityX?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?, // false
    @SerializedName("slot")
    val slot: Int? // 1
)