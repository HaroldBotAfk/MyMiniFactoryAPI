package com.ru.develop.myminifactory.data.myminifactory.models.collections

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collection(
    val id: Int,
    val description: String,
    val name: String
)