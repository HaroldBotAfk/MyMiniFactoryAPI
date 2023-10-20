package com.ru.develop.myminifactory.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ru.develop.myminifactory.R
import com.ru.develop.myminifactory.databinding.FragmentSearchBinding
import com.ru.develop.myminifactory.ui.search.adapter.UserAdapter
import com.ru.develop.myminifactory.utils.AutoClearedValue
import com.ru.develop.myminifactory.utils.textChangedFlow
import com.ru.develop.myminifactory.utils.toast

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels()
    private var userAdapter: UserAdapter by AutoClearedValue()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initList()
        createFlow()

    }

    private fun initList() {
        userAdapter = UserAdapter {username ->
            navigate(username)
        }
        with(binding.userList) {
            adapter = userAdapter
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

    private fun navigate(username: String) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToCollectionsFragment(username))
    }

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoading)
        viewModel.toastException.observe(viewLifecycleOwner) { stringID ->
            userAdapter.items = emptyList()
            toast(stringID)
        }
        viewModel.followUser.observe(viewLifecycleOwner) { remoteUser ->
            userAdapter.items = listOf(remoteUser)
        }
    }

    private fun isLoading(isLoading: Boolean) {
        binding.userList.isVisible = isLoading.not()
        binding.searchProgressBar.isVisible = isLoading
    }

    private fun createFlow() {
        val searchItem = binding.searchFragmentToolbar.menu.findItem(R.id.action_search).actionView as SearchView
        val searchFlow = searchItem.textChangedFlow()


        viewModel.bindFlow(searchFlow)
    }
}