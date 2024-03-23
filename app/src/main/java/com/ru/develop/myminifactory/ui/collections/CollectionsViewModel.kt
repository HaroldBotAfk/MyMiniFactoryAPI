package com.ru.develop.myminifactory.ui.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.CollectionWithAvatar
import com.ru.develop.myminifactory.data.network.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val repository: CollectionsRepository
) : ViewModel() {

    private val followCollectionLiveData = MutableLiveData<List<CollectionWithAvatar>>()
    private val toastExceptionLiveData = MutableLiveData<Int>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val progressViewLiveData = MutableLiveData<String>()

    val followCollection: LiveData<List<CollectionWithAvatar>>
        get() = followCollectionLiveData

    val toastException: LiveData<Int>
        get() = toastExceptionLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val progressView: LiveData<String>
        get() = progressViewLiveData

    fun getUserCollections(username: String) {
        try {
            viewModelScope.launch {
                isLoadingLiveData.postValue(true)

                val collections = repository.getInfoAboutUserCollections(username = username)
                val collectionWithAvatar = repository.createNewListWithPhoto(
                    list = collections,
                    onItemLoaded = {
                        progressViewLiveData.postValue("$it/${collections.count}")
                    }
                )

                followCollectionLiveData.postValue(collectionWithAvatar)
                isLoadingLiveData.postValue(false)
            }
        } catch (e: Throwable) {
            toastExceptionLiveData.postValue(R.string.unknow_error)
            isLoadingLiveData.postValue(false)
        }
    }
}