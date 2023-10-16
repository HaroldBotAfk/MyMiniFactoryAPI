package com.ru.develop.myminifactory.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.databinding.FragmentTestBinding
import com.ru.develop.myminifactory.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestFragment : Fragment(R.layout.fragment_test) {

    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>

    private val binding: FragmentTestBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCreateDocumentLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testButton.setOnClickListener {
            createTXTFile()
        }
    }

    private fun createTXTFile() {
        createDocumentLauncher.launch("New file mirhrim.txt")
    }

    private fun initCreateDocumentLauncher() {
        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument("text/*")
        ) { uri ->
            handleCreateFile(uri)
        }
    }

    private fun handleCreateFile(uri: Uri?) {
        if (uri == null) {
            toast("file not created")
            return
        }

        var text = "первая строка"
        text = "$text\nвторая строка без отступа"
        text = "$text\n третья строка с отступом"

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireContext().contentResolver.openOutputStream(uri)?.bufferedWriter()
                    ?.use {
                        it.write(text)
                    }
            }
        }
    }
}