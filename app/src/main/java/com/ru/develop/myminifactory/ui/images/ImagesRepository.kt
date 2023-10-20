package com.ru.develop.myminifactory.ui.images

import com.ru.develop.myminifactory.data.myminifactory.models.objects.ObjectImages
import com.ru.develop.myminifactory.data.myminifactory.models.objects.RemoteObjects
import com.ru.develop.myminifactory.data.network.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ImagesRepository {

    //Т.к. с MyMiniFactory выдает null при поиске объекта по ID, мы будем запрашивать всю ту же коллекцию, но уже с доп действием:
    //после ответа с сервера мы будем с помощью функции filter искать объект с нужным нам ID.
    suspend fun getObjectImages(objectID: Int, collectionID: Int): List<ObjectImages> {
        return suspendCoroutine { continuation ->
            Networking.myMiniFactoryAPI.getCollectionObjects(collectionID.toString()).enqueue(
                object : Callback<RemoteObjects> {
                    override fun onResponse(
                        call: Call<RemoteObjects>,
                        response: Response<RemoteObjects>
                    ) {
                        if(response.isSuccessful) {
                            val model = response.body()?.objectsList?.find { it.id == objectID }
                            continuation.resume(model!!.imageList)
                        }
                    }

                    override fun onFailure(call: Call<RemoteObjects>, t: Throwable) {
                        continuation.resumeWithException(Throwable("Status code error"))
                    }
                }
            )
        }
    }
}