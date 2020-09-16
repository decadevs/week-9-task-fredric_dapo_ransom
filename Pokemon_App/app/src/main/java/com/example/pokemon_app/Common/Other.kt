package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class Other(
    @SerializedName("dream_world")
    val dreamWorld: DreamWorld?,
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?
)