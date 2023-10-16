package com.ru.develop.myminifactory.ui.modifier.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
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
            .setTitle("Артикул")
            .setMessage("Начальный артикул для первого товара")
            .setNeutralButton("Отмена") { _, _ ->
                dialogListener?.onDialogNeutralClick()
            }
            .setPositiveButton("Ок") { _, _ ->
                Log.d("CheckFinallyStep", "нажал на кнопку")
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