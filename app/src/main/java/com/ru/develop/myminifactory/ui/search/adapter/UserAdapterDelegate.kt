package com.ru.develop.myminifactory.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.models.RemoteUser
import com.ru.develop.myminifactory.databinding.ItemUserBinding

class UserAdapterDelegate(
    private val onItemClick: (username: String) -> Unit
) : AbsListItemAdapterDelegate<RemoteUser, RemoteUser, UserAdapterDelegate.RemoteUserHolder>() {

    override fun isForViewType(
        item: RemoteUser,
        items: MutableList<RemoteUser>,
        position: Int
    ): Boolean {
        return item is RemoteUser
    }

    override fun onCreateViewHolder(parent: ViewGroup): RemoteUserHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RemoteUserHolder(
            binding,
            onItemClick
        )
    }

    override fun onBindViewHolder(
        item: RemoteUser,
        holder: RemoteUserHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class RemoteUserHolder(
        private val binding: ItemUserBinding,
        private val onItemClick: (username: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentUsername: String? = null

        init {
            binding.root.setOnClickListener {
                currentUsername?.let {
                    onItemClick(currentUsername!!)
                }
            }
        }

        fun bind(user: RemoteUser) {
            currentUsername = user.username

            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.userImageItemImageView)

            val name = binding.root.resources?.getString(R.string.name)
            val followers = binding.root.resources?.getString(R.string.followers)
            val collections = binding.root.resources?.getString(R.string.collections)
            val objects = binding.root.resources?.getString(R.string.objects)

            val info =
                "$name ${user.username}\n$followers ${user.followers}\n$collections ${user.totalCollections}\n$objects ${user.objects}"

            binding.userInfoItemTextView.text = info
        }
    }
}