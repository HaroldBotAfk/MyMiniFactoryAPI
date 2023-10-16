package com.ru.develop.myminifactory.ui.search

import android.app.Application
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser
import com.ru.develop.myminifactory.data.network.Networking

class SearchRepository(
    private val application: Application
) {

    //Сделано с механизмом колбеков, вместо ошибки и её обработки, т.к. catch{} в Flow после ошибки прекращает свою работу.
    fun getInfoAboutUser(
        string: String,
        successfulCallback: (remoteUser: RemoteUser) -> Unit,
        errorCallback: (stringId: Int) -> Unit
    ) {
        val response = Networking.myMiniFactoryAPI.getUserBySearch(string).execute()
        if (response.isSuccessful) {
            successfulCallback(response.body()!!)
        } else {
            errorCallback(R.string.username_error)
        }
    }
}