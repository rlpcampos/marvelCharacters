package com.example.marvelcharacters.network

import com.example.marvelcharacters.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest

class NetworkInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp = System.currentTimeMillis().toString()
        val hash = generateHash(timeStamp)
        val request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter(TS_KEY, timeStamp)
            .addQueryParameter(API_KEY, BuildConfig.PUBLIC_KEY)
            .addQueryParameter(HASH_KEY, hash)
            .build()
        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }

    private fun generateHash(timeStamp: String) =
        "$timeStamp${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".md5()

    private fun String.md5(): String {
        val bytes = MessageDigest.getInstance(MD5).digest(toByteArray())
        return BigInteger(1, bytes).toString(16).padStart(32, '0')
    }

    companion object {
        private const val TS_KEY = "ts"
        private const val API_KEY = "apikey"
        private const val HASH_KEY = "hash"
        private const val MD5 = "MD5"
    }
}
