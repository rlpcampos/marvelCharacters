package com.example.marvelcharacters.ui

import androidx.lifecycle.MutableLiveData
import com.example.marvelcharacters.BaseViewModel
import com.example.marvelcharacters.models.CharacterResponse
import com.example.marvelcharacters.repository.CharacterRepository

class CharactersViewModel(private val repository: CharacterRepository) : BaseViewModel() {

    val characterList = MutableLiveData<CharacterResponse>()

    fun fetchCharactersList() {
        launchData {
            val data = repository.fetchCharactersList(1)
            characterList.postValue(data)
        }
    }

}
