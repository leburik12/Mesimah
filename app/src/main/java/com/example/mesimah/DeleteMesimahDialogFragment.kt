package com.example.mesimah

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.mesimah.viewmodel.EditMesimahViewModel
import com.google.android.material.button.MaterialButton

class DeleteMesimahDialogFragment : DialogFragment() {

    private lateinit var viewModel: EditMesimahViewModel

    companion object {
        fun newInstance(viewModel: EditMesimahViewModel): DeleteMesimahDialogFragment {
            val fragment = DeleteMesimahDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.viewModel = viewModel
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.delete_dialog_box, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.background = ContextCompat.getDrawable(requireContext(), R.drawable.dialog_bg)

        val cancleBtn = dialogView.findViewById<MaterialButton>(R.id.btn_dialog_cancel)
        val confirmBtn = dialogView.findViewById<MaterialButton>(R.id.btn_dialog_confirm)

        cancleBtn.setOnClickListener {
            dismiss()
        }

        confirmBtn.setOnClickListener {
            viewModel.deleteTask()
            dismiss()
        }
        return dialog
    }

}