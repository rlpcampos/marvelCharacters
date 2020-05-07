package com.example.marvelcharacters.repository

import com.example.marvelcharacters.database.CharacterDao
import com.example.marvelcharacters.database.CharacterDb
import com.example.marvelcharacters.database.ThumbnailDb
import com.example.marvelcharacters.models.Character
import com.example.marvelcharacters.models.Thumbnail
import com.example.marvelcharacters.network.NetworkApi

class CharacterRepository(private val api: NetworkApi, private val characterDao: CharacterDao) {

    suspend fun fetchCharactersList(page: Int, nameStartsWith: String? = null) =
        api.charactersList(LIMIT_REG, page.selectPage(), nameStartsWith).await()

    suspend fun insertFavorites(character: Character) {
        characterDao.insert(
            CharacterDb(
                character.id,
                name = character.name,
                description = character.description,
                thumbnail = ThumbnailDb(character.thumbnail.path, character.thumbnail.extension)
            )
        )
    }
    fun getFavoriteById(id: Int) = characterDao.getCharacterById(id)

    fun getFavorites(): List<Character> {
        return characterDao.getCharacters().map { characterDb ->
            Character(
                id = characterDb.id,
                description = characterDb.description,
                name = characterDb.name,
                thumbnail = Thumbnail(characterDb.thumbnail.path, characterDb.thumbnail.extension),
                modified = ""
            )
        }
    }

    private fun Int.selectPage(): Int = LIMIT_REG * (this - 1)

    companion object {
        private const val LIMIT_REG = 10
    }
}