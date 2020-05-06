package com.example.marvelcharacters.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CharacterResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: String,
    val copyright: String,
    @Json(name = "data") val dataContainer: CharacterDataContainer,
    val etag: String,
    val status: String
)

@JsonClass(generateAdapter = true)
class CharacterDataContainer(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int,
    @Json(name = "results") val characters: List<Character>
)

@JsonClass(generateAdapter = true)
open class Character(
    val id: Int,
    val thumbnail: Thumbnail,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String? = null,
    val comics: Detail? = null,
    val events: Detail? = null,
    val series: Detail? = null,
    val stories: Detail? = null,
    val urls: List<Url> = listOf()
)

@JsonClass(generateAdapter = true)
class Url(
    val type: String,
    val url: String
)

@JsonClass(generateAdapter = true)
class Thumbnail(
    val extension: String,
    val path: String
)

@JsonClass(generateAdapter = true)
class Detail(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)

@JsonClass(generateAdapter = true)
class Item(
    val name: String,
    val resourceURI: String?,
    val type: String?
)
