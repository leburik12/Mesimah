package com.example.mesimah

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mesimah.databinding.FragmentMesimahEditBinding
import com.example.mesimah.model.MesimahDatabase
import com.example.mesimah.viewmodel.EditMesimahViewModel
import com.example.mesimah.viewmodel.EditMesimahViewModelFactory



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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}