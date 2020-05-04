package com.example.marvelcharacters.factory

import com.example.marvelcharacters.network.NetworkApi
import retrofit2.Retrofit

object RetrofitApiFactory {

    fun build(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }
}
