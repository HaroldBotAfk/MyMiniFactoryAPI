package com.ru.develop.myminifactory.ui.objects

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.data.myminifactory.models.objects.RemoteObjects
import kotlinx.coroutines.launch

class ObjectsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ObjectsRepository()

    private val followObjectLiveData = MutableLiveData<List<Object>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val followObject: LiveData<List<Object>>
        get() = followObjectLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getObjects(collectionID: Int) {
        try {
            viewModelScope.launch {
                isLoadingLiveData.postValue(true)
                followObjectLiveData.postValue(repository.getObjects(collectionID))
                isLoadingLiveData.postValue(false)
            }
        } catch (e: Throwable) {
            isLoadingLiveData.postValue(false)
            //TOAST ERROR
            Log.d("CHECK LIST", "ОШИБКА ${e.message}")
        }
    }
}