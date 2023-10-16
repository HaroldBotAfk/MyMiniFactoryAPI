package com.ru.develop.myminifactory.ui.collections

import android.util.Log
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.CollectionWithAvatar
import com.ru.develop.myminifactory.data.myminifactory.models.collections.RemoteCollections
import com.ru.develop.myminifactory.data.myminifactory.models.objects.RemoteObjects
import com.ru.develop.myminifactory.data.network.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectionsRepository {

    suspend fun getInfoAboutUserCollections(
        username: String
    ): RemoteCollections {
        return suspendCoroutine { continuation ->
            Networking.myMiniFactoryAPI.getUserCollections(username).enqueue(
                object : Callback<RemoteCollections> {
                    override fun onResponse(
                        call: Call<RemoteCollections>,
                        response: Response<RemoteCollections>
                    ) {
                        if (response.isSuccessful) {
                            continuation.resume(response.body()!!)
                            Log.d("JSON COLLECTIONS", "${response.body()!!}")
                        } else {
                            continuation.resumeWithException(Throwable("Status code error"))
                        }
                    }

                    override fun onFailure(call: Call<RemoteCollections>, t: Throwable) {
                        continuation.resumeWithException(t)
                        Log.d("JSON COLLECTIONS", "ОШИБКА")
                    }
                }
            )
        }
    }

    //К нам из метода "getInfoAboutUserCollections" приходит список без фото.
    // Чтобы сопоставить фото c коллекции необходимо узнавать у каждой коллекции её фото
    // и брать на обложку фото с параметром "isPrimary" со значением "True"
    suspend fun createNewListWithPhoto(
        list: RemoteCollections,
        onItemLoaded: (counter: Int) -> Unit
    ): MutableList<CollectionWithAvatar> {
        val returnedList: MutableList<CollectionWithAvatar> = mutableListOf()

        withContext(Dispatchers.IO) {
            var counter = 0

            for (collectionItem in list.collectionList) {
                val collectionID = collectionItem.id.toString()
                val objects = Networking.myMiniFactoryAPI.getCollectionObjects(collectionID).execute().body()!!

                var avatar: String? = ""

                for (objectItem in objects.objectsList) {
                    for (imageItem in objectItem.imageList) {
                        if (imageItem.isPrimary) {
                            avatar = imageItem.original?.url
                            break
                        }
                        break
                    }
                }

                counter += 1
                onItemLoaded(counter)  //Прогресс загрузки

                returnedList.add(
                    CollectionWithAvatar(
                        collectionItem.id,
                        collectionItem.name,
                        collectionItem.description,
                        objects.count,
                        avatar
                    )
                )
            }
        }
        return returnedList
    }
}