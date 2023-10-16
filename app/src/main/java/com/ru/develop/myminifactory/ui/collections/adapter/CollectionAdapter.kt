package com.ru.develop.myminifactory.ui.collections.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.ru.develop.myminifactory.data.models.CollectionWithAvatar

class CollectionAdapter(
    onItemClick: (collectionID: Int) -> Unit
) : AsyncListDifferDelegationAdapter<CollectionWithAvatar>(CollectionWithAvatarCallback()) {

    init {
        delegatesManager
            .addDelegate(CollectionAdapterDelegate(onItemClick))
    }

    class CollectionWithAvatarCallback : DiffUtil.ItemCallback<CollectionWithAvatar>() {
        override fun areItemsTheSame(
            oldItem: CollectionWithAvatar, newItem: CollectionWithAvatar
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CollectionWithAvatar, newItem: CollectionWithAvatar
        ): Boolean {
            return oldItem == newItem
        }
    }
}