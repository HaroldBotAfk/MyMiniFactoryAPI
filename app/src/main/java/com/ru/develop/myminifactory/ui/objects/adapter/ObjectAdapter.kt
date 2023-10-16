package com.ru.develop.myminifactory.ui.objects.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object

class ObjectAdapter(
    private val onItemClick: (objectID: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Object>(ObjectsCallback()) {

    init {
        delegatesManager
            .addDelegate(ObjectAdapterDelegate(onItemClick))
    }

    class ObjectsCallback : DiffUtil.ItemCallback<Object>() {
        override fun areItemsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Object, newItem: Object): Boolean {
            return oldItem == newItem
        }
    }
}