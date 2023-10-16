package com.ru.develop.myminifactory.ui.objects.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.databinding.ItemObjectBinding

class ObjectAdapterDelegate(
    private val onItemClick: (objectID: Int) -> Unit
) : AbsListItemAdapterDelegate<Object, Object, ObjectAdapterDelegate.ObjectsHolder>() {

    override fun isForViewType(
        item: Object,
        items: MutableList<Object>,
        position: Int
    ): Boolean {
        return item is Object
    }

    override fun onCreateViewHolder(parent: ViewGroup): ObjectsHolder {
        val binding = ItemObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObjectsHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        item: Object,
        holder: ObjectsHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ObjectsHolder(
        private val binding: ItemObjectBinding,
        private val onItemClick: (objectID: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentObjectID: Int? = null

        init {
            binding.root.setOnClickListener {
                currentObjectID?.let {
                    onItemClick(currentObjectID!!)
                }
            }
        }

        fun bind(collectionObject: Object) {
            currentObjectID = collectionObject.id

            Glide.with(binding.root)
                .load(collectionObject.imageList[0].original?.url)
                .into(binding.objectImageItemImageView)

            val title = binding.root.resources?.getString(R.string.title)
            val images = binding.root.resources?.getString(R.string.image)

            val info = "$title ${collectionObject.name}\n$images ${collectionObject.imageList.size}"

            binding.objectInfoItemTextView.text = info
        }
    }
}