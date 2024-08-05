package com.example.mesimah

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mesimah.adapter.MesimahItemAdapter
import com.example.mesimah.databinding.FragmentMesimahBinding
import com.example.mesimah.model.MesimahDatabase
import com.example.mesimah.viewmodel.TasksViewModel
import com.example.mesimah.viewmodel.TasksViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MesimahFragment : Fragment() {
    private val TAG: String = "MESIMAH FRAGMENT"
    private var _binding: FragmentMesimahBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMesimahBinding.inflate(inflater, container,false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = MesimahDatabase.getInstance(application).taskDao

        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this,viewModelFactory).get( TasksViewModel::class.java)

        binding.btnOpenDatePicker.setOnClickListener {
            showDateRangePicker(viewModel)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        Log.i(TAG, "Before adapter initialized")
        val adapter = MesimahItemAdapter { taskId ->
           viewModel.onTaskClicked(taskId)
        }

        binding.tasksListRecyclerView.adapter=adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToMesimah.observe(viewLifecycleOwner, Observer { taskId ->
             taskId?.let {
                 val action = MesimahFragmentDirections
                     .actionMesimahFragmentToMesimahEditFragment(taskId)
                 this.findNavController().navigate(action)
                 viewModel.onTaskNavigated()
             }
        })

        return view
    }

    private fun showDateRangePicker(viewModel: TasksViewModel) {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select Date Range")
            .build()

        dateRangePicker.show(parentFragmentManager, "date_range_picker")
        dateRangePicker.addOnPositiveButtonClickListener { dateRange ->
            val startDate = dateRange.first
            val endDate = dateRange.second

            viewModel.setDateRange(startDate, endDate)

            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDateString = formatter.format(Date(startDate))
            val endDateString = formatter.format(Date(endDate))
            val message = "Selected Date Range: $startDateString - $endDateString"
            Toast.makeText(requireContext(), message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        }

}