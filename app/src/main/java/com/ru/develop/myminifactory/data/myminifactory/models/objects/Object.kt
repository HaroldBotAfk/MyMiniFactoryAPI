package com.ru.develop.myminifactory.data.myminifactory.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Object(
    val id: Int,
    val name: String,
    @Json(name = "images")
    val imageList: List<ObjectImages>
)