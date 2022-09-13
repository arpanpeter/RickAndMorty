package com.arpan.rickandmortyapp.screens.characterSearchScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.arpan.rickandmortyapp.Constants
import com.arpan.rickandmortyapp.domain.Event
import com.arpan.rickandmortyapp.screens.characterSearchScreen.CharacterSearchPagingSource.LocalException

class CharacterSearchViewModel : ViewModel() {

    private var currentUserSearch: String = ""
    private var pagingSource: CharacterSearchPagingSource? = null
        get() {
            if (field == null || field?.invalid == true) {
                field = CharacterSearchPagingSource(currentUserSearch) { localException ->

                    _localExceptionEventLiveData.postValue(Event(localException))
                }
            }

            return field
        }

    val flow = Pager(

        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        pagingSource!!
    }.flow.cachedIn(viewModelScope)

    // For error handling propagation
    private val _localExceptionEventLiveData = MutableLiveData<Event<LocalException>>()
    val localExceptionEventLiveData: LiveData<Event<LocalException>> = _localExceptionEventLiveData

    fun submitQuery(userSearch: String) {
        currentUserSearch = userSearch
        pagingSource?.invalidate()
    }

}