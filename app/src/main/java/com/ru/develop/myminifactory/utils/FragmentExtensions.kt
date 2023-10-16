package com.ru.develop.myminifactory.utils

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController

fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(string: String) {
    Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
}

fun Fragment.findNavControllerInFragment(
    @IdRes viewId: Int
): NavController = Navigation.findNavController(requireActivity(), viewId)
