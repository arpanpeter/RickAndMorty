package com.arpan.rickandmortyapp.screens.characterDetailsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpan.rickandmortyapp.characters.CharactersRepository
import com.arpan.rickandmortyapp.domain.models.Character
import kotlinx.coroutines.launch

class CharacterDetailViewModel: ViewModel() {

    private val repository = CharactersRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        val character = repository.getCharacterById(characterId)
        _characterByIdLiveData.postValue(character)
    }

}