package com.ru.develop.myminifactory.ui.modifier

import com.ru.develop.myminifactory.data.models.ModifierObject
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.data.myminifactory.models.objects.RemoteObjects
import com.ru.develop.myminifactory.data.network.Networking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ModifierObjectsRepository {

    suspend fun getObjects(collectionID: Int): List<Object> {
        return suspendCoroutine { continuation ->
            Networking.myMiniFactoryAPI.getCollectionObjects(collectionID.toString()).enqueue(
                object : Callback<RemoteObjects> {
                    override fun onResponse(
                        call: Call<RemoteObjects>,
                        response: Response<RemoteObjects>
                    ) {
                        if (response.isSuccessful) {
                            continuation.resume(response.body()!!.objectsList)
                        } else {
                            continuation.resumeWithException(Throwable("Status code error"))
                        }
                    }

                    override fun onFailure(call: Call<RemoteObjects>, t: Throwable) {
                        continuation.resumeWithException(t)
                    }
                }
            )
        }
    }

    suspend fun getModifierObject(collectionID: Int): List<ModifierObject> {
        return suspendCoroutine { continuation ->
            Networking.myMiniFactoryAPI.getCollectionObjects(collectionID.toString()).enqueue(
                object : Callback<RemoteObjects> {
                    override fun onResponse(
                        call: Call<RemoteObjects>,
                        response: Response<RemoteObjects>
                    ) {
                        if (response.isSuccessful) {
                            val listObject = response.body()?.objectsList!!
                            val listModifierObject = convertObjects(listObject)
                            continuation.resume(listModifierObject)
                        }
                    }

                    override fun onFailure(call: Call<RemoteObjects>, t: Throwable) {
                        continuation.resumeWithException(Throwable("Status code error"))
                    }
                }
            )
        }
    }

    private fun convertObjects(objectList: List<Object>): List<ModifierObject> {
        val returnedList: MutableList<ModifierObject> = mutableListOf()
        for (item in objectList) {
            returnedList.add(
                ModifierObject(
                    item.id,
                    item.name,
                    item.imageList[0].original!!.url,
                    item.imageList.size
                )
            )
        }

        return returnedList
    }

    fun convertObjectsInText(
        modifierList: List<ModifierObject>,
        objectList: List<Object>,
        startArticle: String
    ): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val currentDateAndTime = dateFormat.format(Date())

        var txt = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "\n<yml_catalog date=\"$currentDateAndTime\">" +
                "\n  <shop>" +
                "\n    <name>MIRHRIM</name>" +
                "\n    <company>MIRHRIM</company>" +
                "\n    <url>https://vk.com/mirhrim</url>" +
                "\n    <currencies>" +
                "\n      <currency id=\"RUB\" rate=\"1\"/>" +
                "\n    </currencies>" +
                "\n    <categories>" +
                "\n      <category id=\"10004\">Хобби и развлечения</category>" +
                "\n    </categories>" +
                "\n    <offers>"

        var article = startArticle

        for ((index, item) in modifierList.withIndex()) {
            if (!item.isSeparate) {
                txt += createOffer(item, objectList, article)
                article = articlePlusOne(article)
            } else {
                txt += createOffer(item, objectList, article)
                article = articlePlusOne(article)
                for (imageIndex in 0 until objectList[index].imageList.size) {
                    txt += createSeparateOffer(item, objectList, imageIndex, article)
                    article = articlePlusOne(article)
                }
            }
        }

        txt += "\n    </offers> " +
                "\n  </shop>" +
                "\n</yml_catalog>"

        return txt
    }

    private fun createOffer(
        modifierObject: ModifierObject,
        objectList: List<Object>,
        article: String
    ): String {

        var offer = "\n      <offer id=\"$article\" available=\"true\" group_id=\"219017971\">" +
                "\n        <price>1</price>" +
                "\n        <currencyId>RUB</currencyId>" +
                "\n        <categoryId>10004</categoryId>" +
                "\n        <name>${modifierObject.name}</name>" +
                "\n        <description>$DESCRIPTION</description>"

        val imageList =
            objectList.find { modifierObject.id == it.id }?.imageList  //Поиск нужного списка изображений

        for ((counter, image) in imageList!!.withIndex()) {
            if (counter > 4) {  // > 4 т.к. нумерация индексов начинается с 0, т.е. 0,1,2,3,4 (в сумме 5)
                break
            }
            offer += "\n        <picture>${image.original?.url}</picture>"
        }
        offer += "\n      </offer>"

        return offer
    }

    private fun createSeparateOffer(
        modifierObject: ModifierObject,
        objectList: List<Object>,
        index: Int,
        article: String
    ): String {

        val image =
            objectList.find { modifierObject.id == it.id }?.imageList?.get(index)?.original?.url

        return "\n      <offer id=\"$article\" available=\"true\" group_id=\"219017971\">" +
                "\n        <price>1</price>" +
                "\n        <currencyId>RUB</currencyId>" +
                "\n        <categoryId>10004</categoryId>" +
                "\n        <name>${modifierObject.name} #${index + 1}</name>" +
                "\n        <description>$DESCRIPTION</description>" +
                "\n        <picture>$image</picture>" +
                "\n      </offer>"
    }

    private fun articlePlusOne(article: String): String {
        var chars = article.filter { !it.isDigit() }  //Получение всех символов
        var numbers = article.filter { it.isDigit() }.toInt()  //Получение всех цифр из текста
        numbers += 1

        val difference = article.length - "$chars$numbers".length

        for (i in 0 until difference) {
            chars += 0
        }
        return "$chars$numbers"
    }

    companion object {
        const val DESCRIPTION = "Качественная фотополимерная 8к печать\n\n" +
                "Модель неокрашенна, снята с поддержек\n\n" +
                "В местах снятия поддержек возможны незначительные следы от шлифовки\n\n" +
                "Доставка Почтой России/СДЕКом\n\n" +
                "В случае поломки модели при доставке или другому тому или иному качественному вопросу обращаться в лс группы https://vk.com/mirhrim"
    }
}