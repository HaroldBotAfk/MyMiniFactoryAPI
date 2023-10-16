package com.ru.develop.myminifactory.data.models

data class CollectionWithAvatar(
    val id: Int,
    val name: String,
    val description: String,
    val totalCount: Int,
    val avatarURL: String? = null
)