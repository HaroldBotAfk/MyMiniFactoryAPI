package com.ru.develop.myminifactory.data.network

import com.ru.develop.myminifactory.data.models.CollectionWithAvatar
import com.ru.develop.myminifactory.data.myminifactory.models.collections.RemoteCollections

interface CollectionsRepository {

    suspend fun getInfoAboutUserCollections(
        username: String
    ): RemoteCollections

    suspend fun createNewListWithPhoto(
        list: RemoteCollections,
        onItemLoaded: (counter: Int) -> Unit
    ): MutableList<CollectionWithAvatar>
}