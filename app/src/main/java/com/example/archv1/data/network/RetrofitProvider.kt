package com.example.archv1.data.network

import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {

    companion object {

        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
        private const val CONNECT_TIMEOUT = 5000L
        private const val READ_TIMEOUT = 5000L
        private const val WRITE_TIMEOUT = 5000L
        private val TIME_UNIT = TimeUnit.MILLISECONDS

        fun provideLoginInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }

        fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
            val httpClient = OkHttpClient.Builder()
            httpClient.apply {
                connectTimeout(
                    CONNECT_TIMEOUT,
                    TIME_UNIT
                )
                readTimeout(
                    READ_TIMEOUT,
                    TIME_UNIT
                )
                writeTimeout(
                    WRITE_TIMEOUT,
                    TIME_UNIT
                )
                addInterceptor(loggingInterceptor)
            }
            return httpClient
        }

        fun providePlaceHolderApi(httpClient: OkHttpClient.Builder): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(httpClient.build())
            .build()

        fun provideAlbumService(retrofit: Retrofit): AlbumService = retrofit
            .create(AlbumService::class.java)
    }
}
