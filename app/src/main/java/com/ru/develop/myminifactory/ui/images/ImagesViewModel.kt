package com.ru.develop.myminifactory.ui.images

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.data.myminifactory.models.objects.ObjectImages
import kotlinx.coroutines.launch

class ImagesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ImagesRepository()

    private val followImageLiveData = MutableLiveData<List<ObjectImages>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val followImage: LiveData<List<ObjectImages>>
        get() = followImageLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getObjectImages(objectID: Int, collectionID: Int) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            followImageLiveData.postValue(repository.getObjectImages(objectID, collectionID))
            isLoadingLiveData.postValue(false)
        }
    }
}