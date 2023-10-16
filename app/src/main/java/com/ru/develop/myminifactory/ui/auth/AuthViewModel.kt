package com.ru.develop.myminifactory.ui.auth

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.auth.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(application)

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()

    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastEventChannel.trySendBlocking(R.string.auth_canceled)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        Log.d("Oauth", "3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                Log.d("Oauth","4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
                authRepository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                loadingMutableStateFlow.value = false
                authSuccessEventChannel.send(Unit)
            }.onFailure {
                loadingMutableStateFlow.value = false
                toastEventChannel.send(R.string.auth_canceled)
            }
        }
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = authRepository.getAuthRequest()

        Log.d("Oauth", "1. Generated verifier=${authRequest.codeVerifier}, challenge=${authRequest.codeVerifierChallenge}")

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )

        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
        Log.d("Oauth", "2. Open auth page: ${authRequest.toUri()}")
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}