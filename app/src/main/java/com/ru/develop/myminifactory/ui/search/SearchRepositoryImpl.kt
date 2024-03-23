package com.ru.develop.myminifactory.ui.search

import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.MyMiniFactoryAPI
import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser
import com.ru.develop.myminifactory.data.network.SearchRepository
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val myMiniFactoryAPI: MyMiniFactoryAPI
) : SearchRepository {

    //Сделано с механизмом колбеков, вместо ошибки и её обработки, т.к. catch{} в Flow после ошибки прекращает свою работу.
    override fun getInfoAboutUser(
        string: String,
        successfulCallback: (remoteUser: RemoteUser) -> Unit,
        errorCallback: (stringId: Int) -> Unit
    ) {
        val response = myMiniFactoryAPI.getUserBySearch(string).execute()
        if (response.isSuccessful) {
            successfulCallback(response.body()!!)
        } else {
            errorCallback(R.string.username_error)
        }
    }
}