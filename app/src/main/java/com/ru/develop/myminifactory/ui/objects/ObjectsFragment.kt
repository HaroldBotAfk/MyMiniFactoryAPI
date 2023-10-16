package com.ru.develop.myminifactory.ui.objects

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.databinding.FragmentObjectsBinding
import com.ru.develop.myminifactory.ui.objects.adapter.ObjectAdapter
import com.ru.develop.myminifactory.utils.AutoClearedValue
import com.ru.develop.myminifactory.utils.toast

class ObjectsFragment : Fragment(R.layout.fragment_objects) {

    private val binding: FragmentObjectsBinding by viewBinding(FragmentObjectsBinding::bind)
    private val viewModel: ObjectsViewModel by viewModels()
    private var objectAdapter: ObjectAdapter by AutoClearedValue()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
        initToolbar()

//        viewModel.getObjects(navArgs.collectionID)
    }

    private fun initList() {
        objectAdapter = ObjectAdapter { objectID ->
//            navigate(objectID, navArgs.collectionID)
            Log.d("CHECK LIST", "$objectID")
        }
        with(binding.objectList) {
            adapter = objectAdapter
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

    private fun navigate(objectID: Int, collectionID: Int) {
//        findNavController().navigate(
//            ObjectsFragmentDirections.actionObjectsFragmentToImagesFragment(
//                objectID,
//                collectionID
//            )
//        )
    }

    private fun initToolbar() {
        binding.objectToolbar.setOnMenuItemClickListener { menuItem ->

            when (menuItem.itemId) {
                R.id.action_modify -> {
//                    findNavController().navigate(ObjectsFragmentDirections.actionObjectsFragmentToModifierObjectsFragment(navArgs.collectionID))
                    true
                }
                R.id.action_convert -> {
                    //convert
                    true
                }
                else -> false
            }
        }
    }

    private fun bindViewModel() {
        viewModel.followObject.observe(viewLifecycleOwner) { listObject ->
            Log.d("CHECK LIST", "$listObject")
            objectAdapter.items = listObject
        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoading)
    }

    private fun isLoading(boolean: Boolean) {
        binding.objectList.isVisible = boolean.not()
        binding.objectProgressBar.isVisible = boolean
    }
}