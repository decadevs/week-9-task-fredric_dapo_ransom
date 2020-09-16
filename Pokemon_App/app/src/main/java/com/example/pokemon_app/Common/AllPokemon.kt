package com.example.pokemon_app.Common


import com.google.gson.annotations.SerializedName

data class AllPokemon(
    @SerializedName("abilities")
    val abilities: List<Ability>?,
    @SerializedName("base_experience")
    val baseExperience: Int?, // 64
    @SerializedName("forms")
    val forms: List<Form>?,
    @SerializedName("game_indices")
    val gameIndices: List<GameIndice>?,
    @SerializedName("height")
    val height: Int?, // 7
    @SerializedName("held_items")
    val heldItems: List<Any>?,
    @SerializedName("id")
    val id: Int?, // 1
    @SerializedName("is_default")
    val isDefault: Boolean?, // true
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String?, // https://pokeapi.co/api/v2/pokemon/1/encounters
    @SerializedName("moves")
    val moves: List<Move>?,
    @SerializedName("name")
    val name: String?, // bulbasaur
    @SerializedName("order")
    val order: Int?, // 1
    @SerializedName("species")
    val species: Species?,
    @SerializedName("sprites")
    val sprites: Sprites?,
    @SerializedName("stats")
    val stats: List<Stat>?,
    @SerializedName("types")
    val types: List<Type>?,
    @SerializedName("weight")
    val weight: Int? // 69
)