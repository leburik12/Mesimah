package com.example.mesimah.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mesimah.model.Mesimah
import com.example.mesimah.model.TaskDao
import kotlinx.coroutines.launch

class TasksViewModel(val dao: TaskDao) : ViewModel() {
    private val TAG = "TASKS_VIEW_MODEL"
    private val _navigateToMesimah = MutableLiveData<Long?>()
    val navigateToMesimah: LiveData<Long?>
        get() = _navigateToMesimah

    var newTaskName = ""
    var newTaskDescription = ""
    val tasks = dao.getAll()

    fun addTask() {
        viewModelScope.launch {
            val task = Mesimah()
            task.taskName = newTaskName
            task.taskDescription = newTaskDescription
            Log.i(TAG,"before add task")
            dao.insert(task)
            Log.i(TAG,"after add task")
        }
    }

    fun onTaskClicked(taskId: Long) {
        _navigateToMesimah.value = taskId
    }

    fun onTaskNavigated() {
        _navigateToMesimah.value = null
    }
}