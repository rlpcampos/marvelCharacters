package com.example.marvelcharacters.factory

import android.content.Context
import com.example.marvelcharacters.network.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {

    fun build(context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(NetworkInterceptor())

        builder.addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )

        builder.readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }
}
