package com.ru.develop.myminifactory.ui.images.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.ru.develop.myminifactory.data.myminifactory.models.objects.ObjectImages

class ImageAdapter(

) : AsyncListDifferDelegationAdapter<ObjectImages>(ObjectsCallback()) {

    init {
        delegatesManager
            .addDelegate(ImageAdapterDelegate())
    }

    class ObjectsCallback : DiffUtil.ItemCallback<ObjectImages>() {
        override fun areItemsTheSame(oldItem: ObjectImages, newItem: ObjectImages): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ObjectImages, newItem: ObjectImages): Boolean {
            return oldItem == newItem
        }
    }
}