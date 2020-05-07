package com.example.marvelcharacters.network

import com.example.marvelcharacters.models.CharactersResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("/v1/public/characters")
    fun charactersList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("nameStartsWith") nameStartsWith: String? = null
    ): Deferred<CharactersResponse>
}