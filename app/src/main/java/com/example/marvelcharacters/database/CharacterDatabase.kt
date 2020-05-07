package com.example.marvelcharacters.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CharacterDb::class), version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}