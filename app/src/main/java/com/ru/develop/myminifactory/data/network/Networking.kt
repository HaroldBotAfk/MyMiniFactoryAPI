package com.ru.develop.myminifactory.data.network

import android.content.Context
import com.ru.develop.myminifactory.data.auth.TokenStorage
import com.ru.develop.myminifactory.data.myminifactory.MyMiniFactoryAPI
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {

    private var okhttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null

    val myMiniFactoryAPI: MyMiniFactoryAPI
        get() = retrofit?.create() ?: error("retrofit is not initialized")

    fun init(context: Context) {
        okhttpClient = OkHttpClient.Builder()
//            .addNetworkInterceptor(
//                HttpLoggingInterceptor()
//                    .setLevel(HttpLoggingInterceptor.Level.BODY)
//            )
            .addNetworkInterceptor(AuthorizationInterceptor())
//            .addNetworkInterceptor(AuthorizationFailedInterceptor(AuthorizationService(context), TokenStorage))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.myminifactory.com/api/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okhttpClient!!)
            .build()
    }
}