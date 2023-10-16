package com.ru.develop.myminifactory.ui.objects

import android.util.Log
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.data.myminifactory.models.objects.RemoteObjects
import com.ru.develop.myminifactory.data.network.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ObjectsRepository {

    suspend fun getObjects(collectionID: Int) : List<Object> {
        return suspendCoroutine { continuation ->
            Networking.myMiniFactoryAPI.getCollectionObjects(collectionID.toString()).enqueue(
                object : Callback<RemoteObjects> {
                    override fun onResponse(
                        call: Call<RemoteObjects>,
                        response: Response<RemoteObjects>
                    ) {
                        if (response.isSuccessful) {
                            continuation.resume(response.body()!!.objectsList)
                            Log.d("JSON OBJECTS", "${response.body()!!}")
                        } else {
                            continuation.resumeWithException(Throwable("Status code error"))
                        }
                    }

                    override fun onFailure(call: Call<RemoteObjects>, t: Throwable) {
                        continuation.resumeWithException(t)
                        Log.d("JSON OBJECTS", "ОШИБКА")
                    }
                }
            )
        }
    }
}