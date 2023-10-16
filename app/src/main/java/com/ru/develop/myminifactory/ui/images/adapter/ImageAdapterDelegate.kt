package com.ru.develop.myminifactory.ui.images.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.data.myminifactory.models.objects.ObjectImages
import com.ru.develop.myminifactory.databinding.ItemImageBinding
import com.ru.develop.myminifactory.databinding.ItemObjectBinding

class ImageAdapterDelegate(

) : AbsListItemAdapterDelegate<ObjectImages, ObjectImages, ImageAdapterDelegate.ObjectImageHolder>() {

    override fun isForViewType(
        item: ObjectImages,
        items: MutableList<ObjectImages>,
        position: Int
    ): Boolean {
        return item is ObjectImages
    }

    override fun onCreateViewHolder(parent: ViewGroup): ObjectImageHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObjectImageHolder(binding)
    }

    override fun onBindViewHolder(
        item: ObjectImages,
        holder: ObjectImageHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ObjectImageHolder(
        private val binding: ItemImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(objectImage: ObjectImages) {
            Log.d("IMAGEFRAGMENT CHECK", "bind")

            Glide.with(binding.root)
                .load(objectImage.original?.url)
                .into(binding.imageItemImageView)

        }
    }
}