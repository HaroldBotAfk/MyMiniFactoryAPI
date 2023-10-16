package com.ru.develop.myminifactory.ui.modifier.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.ru.develop.myminifactory.data.models.ModifierObject
import com.ru.develop.myminifactory.data.myminifactory.models.objects.Object
import com.ru.develop.myminifactory.databinding.ItemModifierObjectBinding

class ModifierObjectAdapter(
    private val onItemClick: (objectID: Int) -> Unit,
    private val onItemClickLong: (binding: ItemModifierObjectBinding, objectID: Int) -> Unit
) : AsyncListDifferDelegationAdapter<ModifierObject>(ObjectsCallback()) {

    init {
        delegatesManager
            .addDelegate(ModifierObjectAdapterDelegate(onItemClick, onItemClickLong))
    }

    class ObjectsCallback : DiffUtil.ItemCallback<ModifierObject>() {
        override fun areItemsTheSame(oldItem: ModifierObject, newItem: ModifierObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ModifierObject, newItem: ModifierObject): Boolean {
            return oldItem == newItem
        }
    }
}