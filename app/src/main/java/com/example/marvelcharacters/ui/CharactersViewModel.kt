package com.example.marvelcharacters.ui

import androidx.lifecycle.MutableLiveData
import com.example.marvelcharacters.BaseViewModel
import com.example.marvelcharacters.models.Character
import com.example.marvelcharacters.models.PageControl
import com.example.marvelcharacters.repository.CharacterRepository

class CharactersViewModel(private val repository: CharacterRepository) : BaseViewModel() {

    private var page = 1

    val characterList = MutableLiveData<List<Character>>()
    var hasNextPage = true

    fun fetchCharactersList(firstRequest: Boolean = false) {
        if (loading.value == true || (firstRequest && page != 1)) return
        launchData {
            repository.fetchCharactersList(page, null).also { data ->
                characterList.postValue(data.dataContainer.characters)
                PageControl(data.dataContainer).also {
                    checkPagination(it)
                    this@CharactersViewModel.hasNextPage = it.hasNextPage
                }
            }
        }
    }

    private fun checkPagination(pagination: PageControl) {
        if (pagination.hasNextPage) {
            page++
        }
    }
}
