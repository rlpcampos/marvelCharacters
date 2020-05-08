package com.example.marvelcharacters.repository

import com.example.marvelcharacters.database.CharacterDao
import com.example.marvelcharacters.database.CharacterDb
import com.example.marvelcharacters.database.ThumbnailDb
import com.example.marvelcharacters.factory.MoshiFactory
import com.example.marvelcharacters.models.Character
import com.example.marvelcharacters.models.CharactersResponse
import com.example.marvelcharacters.models.Thumbnail
import com.example.marvelcharacters.network.NetworkApi
import com.google.common.io.Resources.getResource
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class CharacterRepositoryTest {
    val api = mockk<NetworkApi>()
    val characterDao = mockk<CharacterDao>()
    val repository = CharacterRepository(api, characterDao)

    @Test
    fun fetchCharactersList_shouldCall_success() {
        val json = getResource("repository/list_character.json")?.readText()
        val response = MoshiFactory.build().adapter(CharactersResponse::class.java).fromJson(json!!)

        every {
            api.charactersList(any(), any(), any())
        } returns CompletableDeferred(response!!)

        runBlocking {
            assertThat(repository.fetchCharactersList(1).dataContainer.count).isEqualTo(10)
        }
    }

    @Test(expected = HttpException::class)
    fun fetchCharactersList_shouldCall_error() {
        val error: Response<CharactersResponse> =
            Response.error(400, ResponseBody.create(MediaType.parse("application/json"), ""))
        every { api.charactersList(any(), any(), any()) } throws HttpException(error)
        runBlocking { repository.fetchCharactersList(1) }
    }

    @Test
    fun getFavorites_shouldCall_success() {
        val character = Character(
            100, Thumbnail("", ""), "name", "description", "", false, ""
        )
        val response = listOf(
            CharacterDb(
                100, "name", "description", ThumbnailDb("", "")
            )
        )
        every { characterDao.getCharacters() } returns response

        runBlocking {
            assertThat(repository.getFavorites().firstOrNull()?.id).isEqualTo(character.id)
            assertThat(repository.getFavorites().firstOrNull()?.name).isEqualTo(character.name)
            assertThat(repository.getFavorites().firstOrNull()?.description).isEqualTo(character.description)
        }
    }

    @Test(expected = Exception::class)
    fun getFavorites_shouldCall_error() {
        every { characterDao.getCharacters() } throws Exception("error")
        runBlocking { repository.getFavorites() }
    }

}