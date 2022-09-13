package com.arpan.rickandmortyapp.characters

import androidx.paging.DataSource
import com.arpan.rickandmortyapp.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope



class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {

    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharactersDataSource(coroutineScope, repository)
    }
}
