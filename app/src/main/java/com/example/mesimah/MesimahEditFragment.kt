package com.example.mesimah

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mesimah.databinding.FragmentMesimahEditBinding
import com.example.mesimah.model.MesimahDatabase
import com.example.mesimah.viewmodel.EditMesimahViewModel
import com.example.mesimah.viewmodel.EditMesimahViewModelFactory
import com.example.mesimah.viewmodel.TasksViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MesimahEditFragment : Fragment() {
    private var _binding: FragmentMesimahEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMesimahEditBinding.inflate(inflater, container,false)
        val view = binding.root

        val taskId = MesimahEditFragmentArgs.fromBundle(requireArguments()).taskId

        val application = requireNotNull(this.activity).application
        val dao = MesimahDatabase.getInstance(application).taskDao

        val viewModelFactory = EditMesimahViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditMesimahViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btnEditDatePicker.setOnClickListener {
            Log.i("Edit Fragement", "btn delete clicked ....")
            showDateRangePicker(viewModel)
        }

        binding.deleteButton.setOnClickListener {
            Log.i("This is ", "dlete button clicked")
            val dialog =  DeleteMesimahDialogFragment.newInstance(viewModel)
            dialog.show(parentFragmentManager,"Dialog Fragment Tag")
        }

        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_mesimahEditFragment_to_mesimahFragment)
                viewModel.onNavigatedToList()
            }
        })
        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_mesimah_edit, container, false)
    }

    private fun showDateRangePicker(viewModel: EditMesimahViewModel) {

        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Update Date Range")
            .build()
        dateRangePicker.show(parentFragmentManager, "edit_date_range_picker")
        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            val startDate = dateRange.first
            val endDate = dateRange.second

            viewModel.setDateRange(startDate, endDate)
//            binding.startDateText.text = "End Date: ${formatDate(startDate)}"
//            binding.endDateText.text = "Start Date: ${formatDate(endDate)}"
        }
    }

    private fun formatDate(dateMillis: Long?): String {
        return dateMillis?.let {
            val formatter = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
            formatter.format(Date(it))
        } ?: "Not Selected"
    }
//
//    private fun showDeleteConfirmationDialog(viewModel: EditMesimahViewModel) {
//        val dialogView = LayoutInflater.from(context).inflate(R.layout.delete_dialog_box, null)
//        val dialog = Dialog(requireContext())
//        dialog.setContentView(R.layout.delete_dialog_box)
//        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//
//
////        val dialog = AlertDialog.Builder(requireContext())
////            .setView(dialogView)
////            .create()
//
//        dialogView.findViewById<MaterialButton>(R.id.btn_dialog_cancel).setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialogView.findViewById<MaterialButton>(R.id.btn_dialog_confirm).setOnClickListener {
//            viewModel.deleteTask()
//            dialog.dismiss()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}