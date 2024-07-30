package com.example.mesimah.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(tasks: Mesimah)

    @Insert
    suspend fun insertAll(tasks: List<Mesimah>)

    @Delete
    suspend fun delete(task: Mesimah)

    @Update
    suspend fun update(task: Mesimah)

    @Query("Select * From mesimah_table WHERE taskId = :taskId")
    fun get(taskId: Long): LiveData<Mesimah>

    @Query("Select * FROM mesimah_table ORDER BY taskId DESC")
    fun getAll(): LiveData<List<Mesimah>>
}