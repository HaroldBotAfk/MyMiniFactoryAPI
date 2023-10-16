package com.ru.develop.myminifactory.data.auth.models

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)