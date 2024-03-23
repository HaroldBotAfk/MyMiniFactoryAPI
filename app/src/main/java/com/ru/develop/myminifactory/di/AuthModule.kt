package com.ru.develop.myminifactory.di

import android.app.Application
import com.ru.develop.myminifactory.data.auth.AuthRepository
import com.ru.develop.myminifactory.ui.auth.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(ViewModelComponent::class)
class AuthModule {

    @Provides
    fun providesAuthService(application: Application): AuthorizationService {
        return AuthorizationService(application)
    }

    @Provides
    fun providesAuthRepository(impl: AuthRepositoryImpl): AuthRepository {
        return impl
    }
}