package com.ru.develop.myminifactory.ui.modifier.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.data.models.ModifierObject
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.databinding.ItemModifierObjectBinding
import com.ru.develop.myminifactory.databinding.ItemObjectBinding

class ModifierObjectAdapterDelegate(
    private val onItemClick: (objectID: Int) -> Unit,
    private val onItemClickLong: (binding: ItemModifierObjectBinding, objectID: Int) -> Unit
) : AbsListItemAdapterDelegate<ModifierObject, ModifierObject, ModifierObjectAdapterDelegate.ModifierObjectsHolder>() {

    override fun isForViewType(
        item: ModifierObject,
        items: MutableList<ModifierObject>,
        position: Int
    ): Boolean {
        return item is ModifierObject
    }

    override fun onCreateViewHolder(parent: ViewGroup): ModifierObjectsHolder {
        val binding = ItemModifierObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModifierObjectsHolder(binding, onItemClick, onItemClickLong)
    }

    override fun onBindViewHolder(
        item: ModifierObject,
        holder: ModifierObjectsHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ModifierObjectsHolder(
        private val binding: ItemModifierObjectBinding,
        private val onItemClick: (objectID: Int) -> Unit,
        private val onItemClickLong: (binding: ItemModifierObjectBinding, objectID: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentObjectID: Int? = null
        private var currentBinding: ItemModifierObjectBinding? = null

        init {
            binding.root.setOnClickListener {
                currentObjectID?.let {
                    onItemClick(currentObjectID!!)
                }
            }

            binding.root.setOnLongClickListener {
                currentBinding?.let {
                    onItemClickLong(currentBinding!!, currentObjectID!!)
                }
                true
            }
        }

        fun bind(modifierObject: ModifierObject) {
            currentObjectID = modifierObject.id
            currentBinding = binding

            Glide.with(binding.root)
                .load(modifierObject.avatarUrl)
                .into(binding.modifierObjectImageItemImageView)

            val title = binding.root.resources?.getString(R.string.title)
            val images = binding.root.resources?.getString(R.string.image)

            val info = "$title ${modifierObject.name}\n$images ${modifierObject.countImages}"

            binding.modifierObjectInfoItemTextView.text = info
        }
    }
}