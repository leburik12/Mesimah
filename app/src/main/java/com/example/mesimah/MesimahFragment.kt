package com.example.mesimah

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mesimah.adapter.MesimahItemAdapter
import com.example.mesimah.databinding.FragmentMesimahBinding
import com.example.mesimah.model.MesimahDatabase
import com.example.mesimah.viewmodel.TasksViewModel
import com.example.mesimah.viewmodel.TasksViewModelFactory

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
            this,viewModelFactory).get(TasksViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        Log.i(TAG, "Before adapter initialized")
        val adapter = MesimahItemAdapter { taskId ->
           viewModel.onTaskClicked(taskId)
        }
        Log.i(TAG, "after adapter initialized")
        binding.tasksListRecyclerView.adapter=adapter
        Log.i(TAG, "after adapter is set")

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        }

}