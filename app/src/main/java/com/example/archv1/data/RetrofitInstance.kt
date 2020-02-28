package com.example.archv1.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com"
    private const val CONNECT_TIMEOUT = 5000L
    private const val READ_TIMEOUT = 5000L
    private const val WRITE_TIMEOUT = 5000L
    private val TIME_UNIT = TimeUnit.MILLISECONDS

    private val httpClient = OkHttpClient.Builder()

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        httpClient.apply {
            connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
            readTimeout(READ_TIMEOUT, TIME_UNIT)
            writeTimeout(WRITE_TIMEOUT, TIME_UNIT)
            addInterceptor(logging)
        }
    }

    val RetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(httpClient.build())
            .build()
            .create(AlbumService::class.java)
    }
}