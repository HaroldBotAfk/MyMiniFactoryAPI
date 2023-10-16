package com.ru.develop.myminifactory.ui.modifier.dialog

interface DialogListener {

    fun onDialogPositiveClick(
        article: String
    )

    fun onDialogNeutralClick()
}