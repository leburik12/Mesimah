package com.example.mesimah.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="mesimah_table")
data class Mesimah (
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name="task_name")
    var taskName: String = "",

    @ColumnInfo(name="task_done")
    var taskDone: Boolean = false,

    @ColumnInfo(name="task_description")
    var taskDescription: String = ""
)

