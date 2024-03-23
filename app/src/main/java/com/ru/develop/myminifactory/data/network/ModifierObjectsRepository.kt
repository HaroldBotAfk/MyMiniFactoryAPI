package com.ru.develop.myminifactory.data.network

import com.ru.develop.myminifactory.data.models.ModifierObject
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object

interface ModifierObjectsRepository {

    suspend fun getObjects(collectionID: Int): List<Object>
    suspend fun getModifierObject(collectionID: Int): List<ModifierObject>
    fun convertObjectsInText(
        modifierList: List<ModifierObject>,
        objectList: List<Object>,
        startArticle: String
    ): String
}