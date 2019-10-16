package com.my_project.airanimation.di.modules

import com.my_project.airanimation.data.network.AirApiService
import com.my_project.airanimation.utilities.BASE_URL
import com.my_project.airanimation.utilities.CONNECT_TIMEOUT
import com.my_project.airanimation.utilities.READ_TIMEOUT
import com.my_project.airanimation.utilities.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
                connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
            }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
        }
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AirApiService = retrofit.create(
        AirApiService::class.java)

}