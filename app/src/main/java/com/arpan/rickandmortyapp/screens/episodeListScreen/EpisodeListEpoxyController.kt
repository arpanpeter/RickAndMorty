package com.arpan.rickandmortyapp.screens.episodeListScreen

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.arpan.rickandmortyapp.R
import com.arpan.rickandmortyapp.databinding.ModelEpisodeListItemBinding
import com.arpan.rickandmortyapp.databinding.ModelEpisodeListTitleBinding
import com.arpan.rickandmortyapp.domain.models.Episode
import com.arpan.rickandmortyapp.epoxy.ViewBindingKotlinModel
import com.arpan.rickandmortyapp.screens.episodeListScreen.headers.EpisodeUiModel

class EpisodeListEpoxyController(
    private val onEpisodeClicked: (Int) -> Unit
) : PagingDataEpoxyController<EpisodeUiModel>() {

    override fun buildItemModel(currentPosition: Int, item: EpisodeUiModel?): EpoxyModel<*> {
        return when (item!!) {

            is EpisodeUiModel.Item -> {
                val episode = (item as EpisodeUiModel.Item).episode
                EpisodeListItemEpoxyModel(
                    episode = episode,
                    onClick = { episodeId ->
                        onEpisodeClicked(episodeId)
                    }
                ).id("episode_${episode.id}")
            }

            is EpisodeUiModel.Header -> {
                val headerText = (item as EpisodeUiModel.Header).text
                EpisodeListTitleEpoxyModel(headerText).id("header_$headerText")
            }
        }
    }

    data class EpisodeListItemEpoxyModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {
        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.getFormattedSeasonTruncated()

            root.setOnClickListener { onClick(episode.id) }
        }
    }

    data class EpisodeListTitleEpoxyModel(
        val text: String
    ) : ViewBindingKotlinModel<ModelEpisodeListTitleBinding>(R.layout.model_episode_list_title) {
        override fun ModelEpisodeListTitleBinding.bind() {
            textView.text = text
        }
    }
}