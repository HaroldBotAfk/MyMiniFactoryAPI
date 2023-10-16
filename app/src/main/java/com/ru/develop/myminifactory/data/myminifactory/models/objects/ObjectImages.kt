package com.ru.develop.myminifactory.data.myminifactory.models.objects

import com.ru.develop.myminifactory.data.myminifactory.models.Image
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ObjectImages(
    val id: Int,
    @Json(name = "is_primary")
    val isPrimary: Boolean,
    val original: Image? = null,
    val thumbnail: Image? = null,
    val standard: Image? = null
)