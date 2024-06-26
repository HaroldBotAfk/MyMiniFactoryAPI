package com.ru.develop.myminifactory.ui.images

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.databinding.FragmentImagesBinding
import com.ru.develop.myminifactory.ui.images.adapter.ImageAdapter
import com.ru.develop.myminifactory.utils.AutoClearedValue
import com.ru.develop.myminifactory.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment : Fragment(R.layout.fragment_images) {

    private val binding: FragmentImagesBinding by viewBinding()
    private val viewModel: ImagesViewModel by viewModels()
    private var imageAdapter: ImageAdapter by AutoClearedValue()
    private val navArgs: ImagesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()

        viewModel.getObjectImages(navArgs.objectID, navArgs.collectionID)
    }

    private fun initList() {
        imageAdapter = ImageAdapter()
        with(binding.imageList) {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        binding.imagesFragmentToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        viewModel.followImage.observe(viewLifecycleOwner) { imageList ->
            imageAdapter.items = imageList
        }
        viewModel.toastException.observe(viewLifecycleOwner) { stringID ->
            toast(stringID)
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoading)
    }

    private fun isLoading(isLoading: Boolean) {
        binding.imageProgressBar.isVisible = isLoading
        binding.imageList.isVisible = isLoading.not()
    }
}