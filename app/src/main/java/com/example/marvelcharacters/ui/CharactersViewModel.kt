package com.example.marvelcharacters.ui

import androidx.lifecycle.MutableLiveData
import com.example.marvelcharacters.BaseViewModel
import com.example.marvelcharacters.models.Character
import com.example.marvelcharacters.models.PageControl
import com.example.marvelcharacters.repository.CharacterRepository

class CharactersViewModel(private val repository: CharacterRepository) : BaseViewModel() {

    private var page = 1
    private var filter: String? = null

    val characterList = MutableLiveData<List<Character>>()
    var hasNextPage = true
    var showDetail = false

    fun filterByName(filter: String?) {
        this.filter = filter
        this.page = 1
        characterList.value = listOf()
        fetchCharactersList()
    }

    fun clearFilter() {
        this.filter = null
        this.page = 1
        characterList.value = listOf()
        fetchCharactersList()
    }

    fun fetchCharactersList(firstRequest: Boolean = false) {
        if (loading.value == true || (firstRequest && page != 1)) return
        launchData {
            repository.fetchCharactersList(page, filter).also { data ->
                characterList.postValue(data.dataContainer.characters.map {
                    it.apply {
                        isFavorite = repository.getFavoriteById(it.id) != null
                    }
                })
                PageControl(data.dataContainer).also {
                    checkPagination(it)
                    this@CharactersViewModel.hasNextPage = it.hasNextPage
                }
            }
        }
    }

    fun fetchFavoriteCharactersList(hasData: Boolean ) {
        if (loading.value == true || hasData ) return
        launchData {
            repository.getFavorites().also {
                characterList.postValue(it)
            }
        }
    }
    fun addFavorite(character: Character){
        launchData {
            repository.insertFavorites(character)
        }
    }

    private fun checkPagination(pagination: PageControl) {
        if (pagination.hasNextPage) {
            page++
        }
    }
}
