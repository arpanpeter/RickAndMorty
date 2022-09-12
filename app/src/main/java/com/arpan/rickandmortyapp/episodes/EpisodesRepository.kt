package com.arpan.rickandmortyapp.episodes

import com.arpan.rickandmortyapp.domain.mappers.EpisodeMapper
import com.arpan.rickandmortyapp.domain.models.Episode
import com.arpan.rickandmortyapp.network.NetworkLayer
import com.arpan.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.arpan.rickandmortyapp.network.response.GetEpisodeByIdResponse
import com.arpan.rickandmortyapp.network.response.GetEpisodesPageResponse

class EpisodesRepository {

    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)

        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }

    suspend fun getEpisodeById(episodeId: Int) : Episode? {
        val request = NetworkLayer.apiClient.getEpisodeById(episodeId)

        if (!request.isSuccessful) return null

        val characterList = getCharactersFromEpisodeResponse(request.body)
        return EpisodeMapper.buildFrom(
            networkEpisode = request.body,
            networkCharacters = characterList
        )
    }

    private suspend fun getCharactersFromEpisodeResponse(
        episodeByIdResponse: GetEpisodeByIdResponse
    ): List<GetCharacterByIdResponse> {
        val characterList = episodeByIdResponse.characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }

        val request = NetworkLayer.apiClient.getMultipleCharacters(characterList)
        return request.bodyNullable ?: emptyList()
    }

}