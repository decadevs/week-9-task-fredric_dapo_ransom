package com.example.pokemon_app.Retrofit

import com.example.pokemon_app.Model.Pokedex
import retrofit2.http.GET
import java.util.*
import io.reactivex.Observable

interface IPokemonList {
    @get:GET("pokemon/1")
    val listPokemon:Observable<Pokedex>
}