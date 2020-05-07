package com.example.marvelcharacters.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CharacterDb::class), version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object{
        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getDatabase(context: Context): CharacterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "character_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}