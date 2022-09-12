package com.arpan.rickandmortyapp.screens.episodeListScreen.headers

import com.arpan.rickandmortyapp.domain.models.Episode

sealed class EpisodeUiModel {

    class Item(val episode: Episode) : EpisodeUiModel()

    class Header(val text: String) : EpisodeUiModel()

}