package com.example.mesimah.viewmodel

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.mesimah.model.Mesimah
import com.example.mesimah.model.TaskDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditMesimahViewModel (taskId: Long, val dao: TaskDao) : ViewModel() {

    var task = dao.get(taskId)
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList : LiveData<Boolean>
        get() = _navigateToList
//
//    var taskName: MutableLiveData<String> = MutableLiveData()
//    var taskDescription: MutableLiveData<String> = MutableLiveData()
//    var taskDone: MutableLiveData<Boolean> = MutableLiveData()
    var taskStartDate: MutableLiveData<Long> = MutableLiveData()
    var taskEndDate: MutableLiveData<Long> = MutableLiveData()

//    init {
//        task.observeForever {
//            it?.let {
//                taskName.value = it.taskName
//                taskDescription.value = it.taskDescription
//                taskDone.value = it.taskDone
//                taskStartDate.value = it.startDate
//                taskEndDate.value = it.endDate
//            }
//        }
//    }

    fun setDateRange(startDate: Long?, endDate: Long?) {
        task.value?.let {
            it.startDate = startDate ?: it.startDate
            it.endDate = endDate ?: it.endDate
        }
        updateTaskTime()
    }

//    fun setDateRange(startDate: Long, endDate: Long) {
//        taskStartDate.value = startDate
//        taskEndDate.value = endDate
//    }
//    fun formatDate(dateMillis: Long?): String {
//        return dateMillis?.let {
//            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            formatter.format(Date(it))
//        } ?: "Not Selected"
//    }
//    fun updateTask() {
//        task.value?.let {
//            it.taskName = taskName.value ?: it.taskName
//            it.taskDescription = taskDescription.value ?: it.taskDescription
//            it.taskDone = taskDone.value ?: it.taskDone
//            it.startDate = taskStartDate.value ?: it.startDate
//            it.endDate = taskEndDate.value ?: it.endDate
//
//            viewModelScope.launch {
//                dao.update(it)
//                _navigateToList.value = true
//            }
//        }
//    }

    private fun updateTaskTime() {
        viewModelScope.launch {
            dao.update(task.value!!)
        }
    }

    fun updateTask() {
        viewModelScope.launch {
            dao.update(task.value!!)
            _navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            dao.delete(task.value!!)
            _navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }

}