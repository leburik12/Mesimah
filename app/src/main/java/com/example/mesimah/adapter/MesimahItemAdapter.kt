package com.example.mesimah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mesimah.R
import com.example.mesimah.databinding.MesimahItemBinding
import com.example.mesimah.model.Mesimah

class MesimahItemAdapter(val clickListener: (taskId: Long) -> Unit)
    : ListAdapter<Mesimah, MesimahItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        //val item = data[position]
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    //override fun getItemCount() = data.size

    class TaskItemViewHolder(val binding: MesimahItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
      //val taskName = rootView.findViewById<TextView>(R.id.task_name)
      //val taskDone = rootView.findViewById<CheckBox>(R.id.task_done)

        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                //val view = layoutInflater.inflate(R.layout.mesimah_item,parent,false) as CardView
                val binding = MesimahItemBinding.inflate(layoutInflater,parent,false)
                return TaskItemViewHolder(binding)
            }
        }

        fun bind(item: Mesimah, clickListener: (taskId: Long) -> Unit) {
//            taskName.text = item.taskName
//            taskDone.isChecked = item.taskDone
            binding.task = item
            binding.root.setOnClickListener {
                clickListener(item.taskId)
            }
        }
    }


}