package com.ru.develop.myminifactory.di

import com.ru.develop.myminifactory.data.network.CollectionsRepository
import com.ru.develop.myminifactory.data.network.ImagesRepository
import com.ru.develop.myminifactory.data.network.ModifierObjectsRepository
import com.ru.develop.myminifactory.data.network.SearchRepository
import com.ru.develop.myminifactory.ui.collections.CollectionsRepositoryImpl
import com.ru.develop.myminifactory.ui.images.ImagesRepositoryImpl
import com.ru.develop.myminifactory.ui.modifier.ModifierObjectsRepositoryImpl
import com.ru.develop.myminifactory.ui.search.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun providesSearchRepository(impl: SearchRepositoryImpl): SearchRepository {
        return impl
    }

    @Provides
    fun providesModifierObjectsRepository(impl: ModifierObjectsRepositoryImpl): ModifierObjectsRepository {
        return impl
    }

    @Provides
    fun providesImagesRepository(impl: ImagesRepositoryImpl): ImagesRepository {
        return impl
    }

    @Provides
    fun providesCollectionsRepository(impl: CollectionsRepositoryImpl): CollectionsRepository {
        return impl
    }
}