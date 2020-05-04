package com.example.marvelcharacters.di

import com.example.marvelcharacters.BuildConfig
import com.example.marvelcharacters.factory.MoshiFactory
import com.example.marvelcharacters.factory.OkHttpClientFactory
import com.example.marvelcharacters.factory.RetrofitApiFactory
import com.example.marvelcharacters.factory.RetrofitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single(override = true) { OkHttpClientFactory.build(androidContext()) }
    single(override = true) { RetrofitFactory.build(BuildConfig.SERVER_URL, get(), get()) }
    factory(override = true) { RetrofitApiFactory.build(get()) }
    single(override = true) { MoshiFactory.build() }
}