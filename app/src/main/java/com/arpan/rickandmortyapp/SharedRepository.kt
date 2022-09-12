package com.arpan.rickandmortyapp

import com.arpan.rickandmortyapp.domain.mappers.CharacterMapper
import com.arpan.rickandmortyapp.domain.models.Character
import com.arpan.rickandmortyapp.network.NetworkLayer
import com.arpan.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.arpan.rickandmortyapp.network.response.GetEpisodeByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        val networkEpisode = getEpisodesFromCharacterResponse(request.body)
        return CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisode
        )
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