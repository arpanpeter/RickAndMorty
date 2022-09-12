package com.arpan.rickandmortyapp.epoxy

import com.arpan.rickandmortyapp.R
import com.arpan.rickandmortyapp.databinding.ModelLoadingBinding

class LoadingEpoxyModel : ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading) {

    override fun ModelLoadingBinding.bind() {
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}