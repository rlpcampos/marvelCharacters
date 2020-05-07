package com.example.marvelcharacters.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvelcharacters.models.Item

@Entity(tableName = "character_table")
class CharacterDb(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    @Embedded val thumbnail: ThumbnailDb
)

class DetailDb(
    val available: String,
    val collectionURI: String,
    val items: List<Item>,
    val returned: String
)

class ThumbnailDb(
    val extension: String,
    val path: String
)