package com.arpan.rickandmortyapp.network

import com.arpan.rickandmortyapp.domain.models.Character

object RickAndMortyCache {

    val characterMap = mutableMapOf<Int, Character>()

}