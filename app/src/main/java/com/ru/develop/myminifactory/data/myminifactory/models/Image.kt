package com.ru.develop.myminifactory.data.myminifactory.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val url: String,
    val width: String? = null,
    val height: String? = null
)