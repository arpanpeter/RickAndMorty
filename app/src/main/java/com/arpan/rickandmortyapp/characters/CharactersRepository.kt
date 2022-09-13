package com.arpan.rickandmortyapp.characters

import com.arpan.rickandmortyapp.domain.mappers.CharacterMapper
import com.arpan.rickandmortyapp.network.NetworkLayer
import com.arpan.rickandmortyapp.network.RickAndMortyCache
import com.arpan.rickandmortyapp.domain.models.Character
import com.arpan.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.arpan.rickandmortyapp.network.response.GetCharactersPageResponse
import com.arpan.rickandmortyapp.network.response.GetEpisodeByIdResponse


class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int) : GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

    suspend fun getCharacterById(characterId: Int): Character? {

        val cacheCharacter = RickAndMortyCache.characterMap[characterId]
        if (cacheCharacter != null) {
            return cacheCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) return null

        val networkEpisode = getEpisodesFromCharacterResponse(request.body)
        val character = CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisode
        )

        RickAndMortyCache.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode.map {
            it.substring(it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }

}