package com.example.marvelcharacters.di

import androidx.room.Room
import com.example.marvelcharacters.database.CharacterDatabase
import com.example.marvelcharacters.repository.CharacterRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single {
        Room.databaseBuilder(androidApplication(), CharacterDatabase::class.java, "character_database")
            .build()
    }
    single { get<CharacterDatabase>().characterDao() }
    single { CharacterRepository(get(), get()) }
}