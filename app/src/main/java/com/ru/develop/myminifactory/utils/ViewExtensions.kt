package com.ru.develop.myminifactory.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun SearchView.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {

        val searchViewTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trySendBlocking(newText?.toString().orEmpty())
                return true
            }
        }

        this@textChangedFlow.setOnQueryTextListener(searchViewTextListener)
        awaitClose {
            this@textChangedFlow.setOnQueryTextListener(null)
        }
    }
}

fun EditText.textChangedFlow(): Flow<String> {
    return callbackFlow<String> {

        val textChangedListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trySendBlocking(p0?.toString().orEmpty())
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        this@textChangedFlow.addTextChangedListener(textChangedListener)
        awaitClose {  //Вызывается, когда flow отменяется. Удаляем слушатели, чтобы не складывались.
            this@textChangedFlow.removeTextChangedListener(textChangedListener)
        }
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}