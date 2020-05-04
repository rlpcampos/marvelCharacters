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
    val count: String,
    val limit: String,
    val offset: String,
    @Json(name = "results") val characters: List<Character>,
    val total: String
)

@JsonClass(generateAdapter = true)
class Character(
    val id: String,
    val description: String,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val comics: Detail?,
    val events: Detail?,
    val series: Detail?,
    val stories: Detail?,
    val thumbnail: Thumbnail,
    val urls: List<Url>
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
