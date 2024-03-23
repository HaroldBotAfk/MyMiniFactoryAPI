package com.ru.develop.myminifactory.data.network

import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser

interface SearchRepository {

    fun getInfoAboutUser(
        string: String,
        successfulCallback: (remoteUser: RemoteUser) -> Unit,
        errorCallback: (stringId: Int) -> Unit
    )
}