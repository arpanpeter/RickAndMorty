package com.arpan.rickandmortyapp.screens.episodeDetailsScreen

import com.airbnb.epoxy.EpoxyController
import com.arpan.rickandmortyapp.R
import com.arpan.rickandmortyapp.databinding.ModelCharacterListItemBinding
import com.arpan.rickandmortyapp.domain.models.Character
import com.arpan.rickandmortyapp.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class EpisodeDetailEpoxyController(
    private val characterList: List<Character>
) : EpoxyController() {

    override fun buildModels() {
        characterList.forEach { character ->
            CharacterEpoxyModel(
                imageUrl = character.image,
                name = character.name
            ).id(character.id).addTo(this)
        }
    }

    data class CharacterEpoxyModel(
        val imageUrl: String,
        val name: String
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {

        override fun ModelCharacterListItemBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name

            root.setOnClickListener {
                null
            }
        }
    }

}