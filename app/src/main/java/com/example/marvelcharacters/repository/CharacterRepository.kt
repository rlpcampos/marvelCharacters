package com.example.marvelcharacters.repository

import com.example.marvelcharacters.network.NetworkApi

class CharacterRepository(private val api: NetworkApi) {

    suspend fun fetchCharactersList(page: Int, nameStartsWith: String? = null) =
        api.charactersList(LIMIT_REG, page.selectPage(), nameStartsWith).await()

    private fun Int.selectPage(): Int = LIMIT_REG * (this - 1)

    companion object {
        private const val LIMIT_REG = 10
    }
}