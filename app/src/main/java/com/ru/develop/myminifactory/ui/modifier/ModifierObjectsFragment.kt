package com.ru.develop.myminifactory.ui.modifier

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.databinding.FragmentModifierObjectsBinding
import com.ru.develop.myminifactory.databinding.ItemModifierObjectBinding
import com.ru.develop.myminifactory.ui.modifier.adapter.ModifierObjectAdapter
import com.ru.develop.myminifactory.ui.modifier.dialog.ConvertDialogFragment
import com.ru.develop.myminifactory.ui.modifier.dialog.DialogListener
import com.ru.develop.myminifactory.utils.AutoClearedValue
import com.ru.develop.myminifactory.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ModifierObjectsFragment : Fragment(R.layout.fragment_modifier_objects), DialogListener {

    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>

    private val binding: FragmentModifierObjectsBinding by viewBinding(
        FragmentModifierObjectsBinding::bind
    )
    private val viewModel: ModifierObjectsViewModel by viewModels()
    private var objectsAdapter: ModifierObjectAdapter by AutoClearedValue()
    private val navArgs: ModifierObjectsFragmentArgs by navArgs()

    private var objectText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCreateDocumentLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        bindViewModel()
        initToolbar()

        viewModel.getModifierObject(navArgs.collectionID)
    }

    override fun onDialogPositiveClick(article: String) {
        if (article.isNotBlank()) {
            convertObjectsInYMLFile(article)
        } else {
            toast(getString(R.string.need_fill_field))
        }
    }

    override fun onDialogNeutralClick() {
        toast(getString(R.string.canceled))
    }

    private fun initList() {
        objectsAdapter = ModifierObjectAdapter(
            onItemClick = { objectID ->
                navigate(objectID)
            },
            onItemClickLong = { itemModifierBinding, objectID ->
                checkIsSeparate(itemModifierBinding, objectID)
            }
        )
        with(binding.modifierObjectList) {
            adapter = objectsAdapter
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
        binding.modifierObjectToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }


        viewModel.followModifierObject.observe(viewLifecycleOwner) { objectList ->
            objectsAdapter.items = objectList
        }
        viewModel.objectText.observe(viewLifecycleOwner) { objectText ->
            this.objectText = objectText
            createDocumentLauncher.launch("New mirhrim file ${navArgs.collectionID}.xml")
        }
        viewModel.toastException.observe(viewLifecycleOwner) { stringID ->
            toast(stringID)
        }
        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoading)
    }

    private fun initToolbar() {
        binding.modifierObjectToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_convert -> {
                    openDialogFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun initCreateDocumentLauncher() {
        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument("text/*")
        ) { uri ->
            handleCreateFile(uri)
            toast(getString(R.string.file_created))
        }
    }

    private fun handleCreateFile(uri: Uri?) {

        if (uri == null) {
            toast(getString(R.string.file_doesnt_created))
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireContext().contentResolver.openOutputStream(uri)?.bufferedWriter()
                    ?.use {
                        it.write(objectText)
                    }
            }
        }
    }

    private fun openDialogFragment() {
        ConvertDialogFragment()
            .show(childFragmentManager, ConvertDialogFragment.TAG)
    }

    private fun convertObjectsInYMLFile(article: String) {
        viewModel.convertObjects(objectsAdapter.items, navArgs.collectionID, article)
    }

    private fun navigate(objectID: Int) {
        findNavController().navigate(
            ModifierObjectsFragmentDirections.actionModifierObjectsFragmentToImagesFragment(
                objectID,
                navArgs.collectionID
            )
        )
    }

    private fun checkIsSeparate(bind: ItemModifierObjectBinding, objectID: Int) {
        val item = objectsAdapter.items.find { it.id == objectID }

        if (!item!!.isSeparate) {
            item.isSeparate = true
            bind.root.setBackgroundColor(requireContext().getColor(R.color.gray))
        } else {
            bind.root.setBackgroundColor(requireContext().getColor(R.color.whiteGray))
            item.isSeparate = false
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.modifierObjectProgressBar.isVisible = isLoading
        binding.modifierObjectList.isVisible = isLoading.not()
    }


}