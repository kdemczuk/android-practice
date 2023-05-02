package com.example.practice.users.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val baseUrl =  "https://jsonplaceholder.typicode.com"

    @Provides
    fun provideHttpLoggingInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .apply {
                addNetworkInterceptor(loggingInterceptor)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        return Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

}