package com.ru.develop.myminifactory.ui.collections

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.databinding.FragmentCollectionsBinding
import com.ru.develop.myminifactory.ui.collections.adapter.CollectionAdapter
import com.ru.develop.myminifactory.utils.AutoClearedValue
import com.ru.develop.myminifactory.utils.toast

class CollectionsFragment : Fragment(R.layout.fragment_collections) {

    private val args: CollectionsFragmentArgs by navArgs()
    private val viewModel: CollectionsViewModel by viewModels()
    private val binding: FragmentCollectionsBinding by viewBinding(FragmentCollectionsBinding::bind)
    private var collectionAdapter: CollectionAdapter by AutoClearedValue()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()

        viewModel.getUserCollections(args.username)
    }

    private fun initList() {
        collectionAdapter = CollectionAdapter { collectionID ->
            navigate(collectionID)
        }
        with(binding.collectionList) {
            adapter = collectionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun bindViewModel() {
        viewModel.followCollection.observe(viewLifecycleOwner) { collectionList ->
            collectionAdapter.items = collectionList
        }
        viewModel.toastException.observe(viewLifecycleOwner) { stringID ->
            collectionAdapter.items = emptyList()
            toast(stringID)
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoading)
    }

    private fun navigate(collectionID: Int) {
        findNavController().navigate(CollectionsFragmentDirections.actionCollectionsFragmentToModifierObjectsFragment(collectionID))
    }

    private fun isLoading(isLoading: Boolean) {
        binding.collectionList.isVisible = isLoading.not()
        binding.collectionsProgressBar.isVisible = isLoading
    }
}