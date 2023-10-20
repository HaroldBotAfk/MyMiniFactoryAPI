package com.ru.develop.myminifactory.ui.modifier.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.ru.develop.myminifactory.R

class ConvertDialogFragment : DialogFragment() {

    private val dialogListener: DialogListener?
        get() = parentFragment?.let { it as? DialogListener }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_convert_objects, null)

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle(getString(R.string.article))
            .setMessage(getString(R.string.first_article_for_offer))
            .setNeutralButton(getString(R.string.cancel)) { _, _ ->
                dialogListener?.onDialogNeutralClick()
            }
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                val article = view.findViewById<EditText>(R.id.articleEditText).text.toString()
                dialogListener?.onDialogPositiveClick(
                    article
                )
            }
            .create()
    }

    companion object {
        const val TAG = "ConvertDialogFragment"
    }
}