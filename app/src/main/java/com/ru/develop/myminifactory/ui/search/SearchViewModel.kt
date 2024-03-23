package com.ru.develop.myminifactory.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser
import com.ru.develop.myminifactory.data.network.SearchRepository
import com.ru.develop.myminifactory.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.job
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private var flowJob: Job? = null

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val toastExceptionLivaData = SingleLiveEvent<Int>()
    private val followUserLiveData = MutableLiveData<RemoteUser>()

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val toastException: LiveData<Int>
        get() = toastExceptionLivaData

    val followUser: LiveData<RemoteUser>
        get() = followUserLiveData


    fun bindFlow(textFlow: Flow<String>) {

        flowJob = textFlow
            .debounce(2000)  //Выполнение Flow после 2х секунд
            .distinctUntilChanged()  //Flow не выполняется если друг за другом идут одинаковы значения
            .onEach { username ->
                if (username.isNotBlank()) {

                    isLoadingLiveData.postValue(true)
                    repository.getInfoAboutUser(
                        username,
                        successfulCallback = { remoteUser ->
                            isLoadingLiveData.postValue(false)
                            followUserLiveData.postValue(remoteUser)
                        },
                        errorCallback = { stringId ->
                            isLoadingLiveData.postValue(false)
                            toastExceptionLivaData.postValue(stringId)
                        }
                    )

                }
            }
            .catch {
                isLoadingLiveData.postValue(false)
                toastExceptionLivaData.postValue(R.string.unknow_error)
            }
            .launchIn(viewModelScope + Dispatchers.IO)
            .job
    }

    override fun onCleared() {
        super.onCleared()
        flowJob?.cancel()
    }
}