package com.arpan.rickandmortyapp.domain.mappers

import com.arpan.rickandmortyapp.domain.models.Character
import com.arpan.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.arpan.rickandmortyapp.network.response.GetEpisodeByIdResponse


/**
 * This class is for converting the response
 * from the GetCharacterByIdResponse network
 * into an internal Character class
 * */
object CharacterMapper {

    fun buildFrom(
        response: GetCharacterByIdResponse,
        episodes: List<GetEpisodeByIdResponse> = emptyList()
    ): Character {
        return Character(
            episodeList = episodes.map {
                EpisodeMapper.buildFrom(it)
            },
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }

}