package com.ru.develop.myminifactory.data.myminifactory.models.collections

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteCollections(
    @Json(name = "total_count")
    val count: Int,
        @Json(name = "items")
    val collectionList: List<Collection>
)