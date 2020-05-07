package com.example.marvelcharacters.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {
    @Query("SELECT * from character_table ORDER BY name ASC")
    fun getCharacters(): List<CharacterDb>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(characterDb: CharacterDb)

    @Query("DELETE FROM character_table")
    suspend fun deleteAll()
}