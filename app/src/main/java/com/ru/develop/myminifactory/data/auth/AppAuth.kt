package com.ru.develop.myminifactory.data.auth

import android.net.Uri
import androidx.core.net.toUri
import com.ru.develop.myminifactory.data.auth.models.TokensModel
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import kotlin.coroutines.suspendCoroutine

object AppAuth {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTH_URI),
        Uri.parse(AuthConfig.TOKEN_URI),
        null
    )

    fun getAuthRequest(): AuthorizationRequest {
        val redirectedUri = AuthConfig.CALLBACK_URL.toUri()
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectedUri
        )
            .setScope(AuthConfig.SCOPE)
            .build()
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretBasic(AuthConfig.CLIENT_SECRET)
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ): TokensModel {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, ex ->
                when {
                    response != null -> {
                        //получение токена произошло успешно
                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))
                    }
                    //получение токенов произошло неуспешно, показываем ошибку
                    ex != null -> { continuation.resumeWith(Result.failure(ex)) }
                    else -> error("unreachable")
                }
            }
        }
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(AuthConfig.SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    object AuthConfig {
        const val AUTH_URI = "https://auth.myminifactory.com/web/authorize"
        const val TOKEN_URI = "https://auth.myminifactory.com/v1/oauth/tokens"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "user,collection,object,objects"
        const val CLIENT_ID = "application_name"
        const val CLIENT_SECRET = "ImoNElTU4zo1bjaqgrWLMucGhqBZP9"
        const val CALLBACK_URL = "ru.mirhrim.oauth://myminifactory.ru/callback"
    }
}
