package com.example.pokemon_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//            .apply {
//                .replace(R.id.list_pokemon_fragment, PokemonList())
//            addToBackStack(null)
//                .commit()
//        }
//        }
        toolbar.title = "POKEMON LIST"
        setSupportActionBar(toolbar)

    }
}