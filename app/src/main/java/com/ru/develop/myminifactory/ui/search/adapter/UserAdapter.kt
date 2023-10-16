package com.ru.develop.myminifactory.ui.search.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser

class UserAdapter(
    onItemClick: (username: String) -> Unit
) : AsyncListDifferDelegationAdapter<RemoteUser>(UserCallback()) {

    init {
        delegatesManager
            .addDelegate(UserAdapterDelegate(onItemClick))
    }

    class UserCallback : DiffUtil.ItemCallback<RemoteUser>() {
        override fun areItemsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem == newItem
        }
    }
}