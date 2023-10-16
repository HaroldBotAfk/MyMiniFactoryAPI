package com.ru.develop.myminifactory.ui.collections

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.CollectionWithAvatar
import kotlinx.coroutines.launch

class CollectionsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CollectionsRepository()

    private val followCollectionLiveData = MutableLiveData<List<CollectionWithAvatar>>()
    private val toastExceptionLiveData = MutableLiveData<Int>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val followCollection: LiveData<List<CollectionWithAvatar>>
        get() = followCollectionLiveData

    val toastException: LiveData<Int>
        get() = toastExceptionLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getUserCollections(username: String) {
        try {
            viewModelScope.launch {
                isLoadingLiveData.postValue(true)

                val collections = repository.getInfoAboutUserCollections(username = username)
                val collectionWithAvatar = repository.createNewListWithPhoto(
                    list = collections, onItemLoaded = {
                        Log.d("onItemLoadedddd", "$it/${collections.count}")
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