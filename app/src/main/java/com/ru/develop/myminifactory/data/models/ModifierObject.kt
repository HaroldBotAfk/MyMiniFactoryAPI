package com.ru.develop.myminifactory.data.models

data class ModifierObject(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val countImages: Int,
    var isSeparate: Boolean = false
)
