package com.example.marvelcharacters.di

import com.example.marvelcharacters.repository.CharacterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { CharacterRepository(get()) }
}