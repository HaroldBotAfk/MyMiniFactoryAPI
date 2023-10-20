package com.ru.develop.myminifactory.ui.modifier

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.ModifierObject
import kotlinx.coroutines.launch

class ModifierObjectsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ModifierObjectsRepository()

    private val followModifierObjectLiveData = MutableLiveData<List<ModifierObject>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val toastExceptionLiveData = MutableLiveData<Int>()
    private val objectTextLiveData = MutableLiveData<String>()

    val followModifierObject: LiveData<List<ModifierObject>>
        get() = followModifierObjectLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val toastException: LiveData<Int>
        get() = toastExceptionLiveData

    val objectText: LiveData<String>
        get() = objectTextLiveData

    fun getModifierObject(collectionID: Int) {
        try {
            viewModelScope.launch {
                isLoadingLiveData.postValue(true)

                val list = repository.getModifierObject(collectionID)
                followModifierObjectLiveData.postValue(list)

                isLoadingLiveData.postValue(false)
            }
        } catch (e: Throwable) {
            toastExceptionLiveData.postValue(R.string.unknow_error)
            isLoadingLiveData.postValue(false)
        }
    }

    fun convertObjects(
        modifierObjectList: List<ModifierObject>,
        collectionID: Int,
        article: String
    ) {
        try {
            viewModelScope.launch {
                isLoadingLiveData.postValue(true)

                val list = repository.getObjects(collectionID)
                val objectText = repository.convertObjectsInText(modifierObjectList, list, article)
                objectTextLiveData.postValue(objectText)

                isLoadingLiveData.postValue(false)
            }
        } catch (e: Throwable) {
            toastExceptionLiveData.postValue(R.string.unknow_error)
            isLoadingLiveData.postValue(false)
        }
    }
}