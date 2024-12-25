package com.example.dailyplanner.presentation.screens.calendar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyplanner.databinding.TaskDayBinding
import com.example.dailyplanner.entity.Hour
import com.example.dailyplanner.entity.Constants
import com.example.dailyplanner.entity.Task
import java.text.SimpleDateFormat

class TaskDayAdapter(private val onClickFull: (Task) -> Unit) :
    ListAdapter<Hour, TaskDayViewHolder>(TaskDayDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskDayViewHolder {
        return TaskDayViewHolder(
            TaskDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskDayViewHolder, position: Int) {
        val item = getItem(position)
        val adapter = TaskHourAdapter { task -> onClick(task) }
        with(holder.binding) {
            // преобразование времени в нужный формат
            time.text = "${SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(item.hour)}-${
                SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(item.hour + Constants.HOUR)
            }"

            taskDescription.adapter = adapter

            adapter.submitList(item.tasks)
        }
    }

    // обработка нажатия на дочерний элемент
    private fun onClick(taskDB: Task) {
        onClickFull(taskDB)
    }
}

class TaskDayViewHolder(val binding: TaskDayBinding) : RecyclerView.ViewHolder(binding.root)

class TaskDayDiffUtilCallback : DiffUtil.ItemCallback<Hour>() {
    override fun areItemsTheSame(oldItem: Hour, newItem: Hour): Boolean {
        return oldItem.hour == newItem.hour
    }

    override fun areContentsTheSame(oldItem: Hour, newItem: Hour): Boolean {
        return oldItem == newItem
    }

}