package com.ru.develop.myminifactory.app

import android.app.Application
import com.ru.develop.myminifactory.data.network.Networking

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Networking.init(this)
    }
}