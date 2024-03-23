package com.ru.develop.myminifactory.data.network

import com.ru.develop.myminifactory.data.myminifactory.models.objects.ObjectImages

interface ImagesRepository {

    suspend fun getObjectImages(objectID: Int, collectionID: Int): List<ObjectImages>
}