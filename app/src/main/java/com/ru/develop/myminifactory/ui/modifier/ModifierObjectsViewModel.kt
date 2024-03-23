package com.ru.develop.myminifactory.ui.modifier

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.ModifierObject
import com.ru.develop.myminifactory.data.network.ModifierObjectsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifierObjectsViewModel @Inject constructor(
    private val repository: ModifierObjectsRepository
) : ViewModel() {

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