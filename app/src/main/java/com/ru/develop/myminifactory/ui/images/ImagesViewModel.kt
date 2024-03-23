package com.ru.develop.myminifactory.ui.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.models.objects.ObjectImages
import com.ru.develop.myminifactory.data.network.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val repository: ImagesRepository
) : ViewModel() {

    private val followImageLiveData = MutableLiveData<List<ObjectImages>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val toastExceptionLiveData = MutableLiveData<Int>()

    val followImage: LiveData<List<ObjectImages>>
        get() = followImageLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val toastException: LiveData<Int>
        get() = toastExceptionLiveData

    fun getObjectImages(objectID: Int, collectionID: Int) {
        try {
            viewModelScope.launch {
                isLoadingLiveData.postValue(true)
                followImageLiveData.postValue(repository.getObjectImages(objectID, collectionID))

                isLoadingLiveData.postValue(false)
            }
        } catch (e: Throwable) {
            toastExceptionLiveData.postValue(R.string.unknow_error)
            isLoadingLiveData.postValue(false)
        }
    }
}