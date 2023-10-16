package com.ru.develop.myminifactory.ui.collections.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.CollectionWithAvatar
import com.ru.develop.myminifactory.databinding.ItemCollectionBinding

class CollectionAdapterDelegate(
    private val onItemClick: (collectionID: Int) -> Unit
) : AbsListItemAdapterDelegate<CollectionWithAvatar, CollectionWithAvatar, CollectionAdapterDelegate.CollectionWithAvatarHolder>() {

    override fun isForViewType(
        item: CollectionWithAvatar,
        items: MutableList<CollectionWithAvatar>,
        position: Int
    ): Boolean {
        return item is CollectionWithAvatar
    }

    override fun onCreateViewHolder(parent: ViewGroup): CollectionWithAvatarHolder {
        val binding =
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionWithAvatarHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        item: CollectionWithAvatar,
        holder: CollectionWithAvatarHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CollectionWithAvatarHolder(
        private val binding: ItemCollectionBinding,
        private val onItemClick: (collectionID: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentCollectionID: Int? = null

        init {
            binding.root.setOnClickListener {
                currentCollectionID?.let {
                    onItemClick(currentCollectionID!!)
                }
            }
        }

        fun bind(collection: CollectionWithAvatar) {
            currentCollectionID = collection.id

            Glide.with(binding.root)
                .load(collection.avatarURL)
                .into(binding.collectionImageItemImageView)

            val title = binding.root.resources?.getString(R.string.title)
            val objects = binding.root.resources?.getString(R.string.objects)

            val info = "$title ${collection.name}\n$objects ${collection.totalCount}"

            binding.collectionInfoItemTextView.text = info

        }
    }
}