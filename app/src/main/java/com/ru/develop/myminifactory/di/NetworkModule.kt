package com.ru.develop.myminifactory.di

import com.ru.develop.myminifactory.data.myminifactory.MyMiniFactoryAPI
import com.ru.develop.myminifactory.data.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(AuthorizationInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.myminifactory.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @Provides
    fun providesMyMiniFactoryAPI(retrofit: Retrofit): MyMiniFactoryAPI {
        return retrofit.create()
    }
}