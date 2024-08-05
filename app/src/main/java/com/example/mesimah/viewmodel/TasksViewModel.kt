package com.example.mesimah.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mesimah.model.Mesimah
import com.example.mesimah.model.TaskDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TasksViewModel(val dao: TaskDao) : ViewModel() {
    private val TAG = "TASKS_VIEW_MODEL"
    private val _navigateToMesimah = MutableLiveData<Long?>()
    val navigateToMesimah: LiveData<Long?>
        get() = _navigateToMesimah

    var newTaskName = ""
    var newTaskDescription = ""
    var start_date: Long = 0L
    var end_date: Long = 0L
    val tasks = dao.getAll()

    fun addTask() {
        viewModelScope.launch {
            val task = Mesimah(
                taskName = newTaskName,
                taskDescription = newTaskDescription,
                startDate = start_date,
                endDate = end_date
            )
            Log.i(TAG,"before add task")
            dao.insert(task)
            Log.i(TAG,"after add task")
        }
    }

    fun setDateRange(start: Long, end: Long) {
        start_date = start
        end_date = end
    }


    fun onTaskClicked(taskId: Long) {
            _navigateToMesimah.value = taskId
    }

    fun onTaskNavigated() {
        _navigateToMesimah.value = null
    }
}