package com.example.mesimah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mesimah.model.TaskDao

class EditMesimahViewModelFactory (private val taskId: Long,
    private val dao: TaskDao) : ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditMesimahViewModel::class.java)) {
                return EditMesimahViewModel(taskId, dao) as T
            }
            throw IllegalArgumentException("Unknow ViewModel")
        }
}