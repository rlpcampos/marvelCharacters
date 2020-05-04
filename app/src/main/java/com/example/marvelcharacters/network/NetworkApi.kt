package com.example.marvelcharacters.network

import com.example.marvelcharacters.models.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {
    @GET("/v1/public/characters")
    fun charactersList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Deferred<CharacterResponse>

    @GET("/v1/public/characters/{characterId}")
    fun charactersDetail(@Path("characterId") characterId: String): Deferred<CharacterResponse>
}