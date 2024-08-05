package com.example.mesimah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName="mesimah_table")
data class Mesimah (
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name="task_name")
    var taskName: String = "",

    @ColumnInfo(name="task_done")
    var taskDone: Boolean = false,

    @ColumnInfo(name="task_description")
    var taskDescription: String = "",

    @ColumnInfo(name="start_date")
    var startDate: Long,

    @ColumnInfo(name="end_date")
    var endDate: Long
) {
    fun formatDate(date: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(date))
    }
}

