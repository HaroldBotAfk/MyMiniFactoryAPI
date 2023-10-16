package com.ru.develop.myminifactory.data.myminifactory.models.objects

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteObjects(
    @Json(name = "total_count")
    val count: Int,
    @Json(name = "items")
    val objectsList: List<Object>
)