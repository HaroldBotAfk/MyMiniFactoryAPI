package com.ru.develop.myminifactory.data.myminifactory.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteUser(
    val username: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    val followers: Int,
    val objects: Int,
    @Json(name = "total_collections")
    val totalCollections: Int
)