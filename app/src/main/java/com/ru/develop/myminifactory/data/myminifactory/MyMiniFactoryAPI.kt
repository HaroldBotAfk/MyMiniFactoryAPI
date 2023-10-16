package com.ru.develop.myminifactory.data.myminifactory

import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser
import com.ru.develop.myminifactory.data.myminifactory.models.collections.RemoteCollections
import com.ru.develop.myminifactory.data.myminifactory.models.objects.RemoteObjects
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyMiniFactoryAPI {

    @GET("users/{username}")
    fun getUserBySearch(
        @Path("username") username: String
    ): Call<RemoteUser>

    @GET("users/{username}/collections")
    fun getUserCollections(
        @Path("username") username: String
    ) : Call<RemoteCollections>

    @GET("collections/{collection_id}/objects")
    fun getCollectionObjects(
        @Path("collection_id") collectionID: String
    ) : Call<RemoteObjects>

}