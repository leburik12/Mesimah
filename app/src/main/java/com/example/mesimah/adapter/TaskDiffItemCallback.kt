package com.example.mesimah.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.mesimah.model.Mesimah

class TaskDiffItemCallback : DiffUtil.ItemCallback<Mesimah>() {
    override fun areItemsTheSame(oldItem: Mesimah, newItem: Mesimah)
        = (oldItem.taskId == newItem.taskId)

    override fun areContentsTheSame(oldItem: Mesimah, newItem: Mesimah)
       = (oldItem == newItem)

}