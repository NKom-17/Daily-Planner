package com.example.dailyplanner.presentation.screens.calendar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyplanner.databinding.TaskHourBinding
import com.example.dailyplanner.entity.Constants
import com.example.dailyplanner.entity.Task
import java.text.SimpleDateFormat

class TaskHourAdapter(private val onClick: (Task) -> Unit) :
    ListAdapter<Task, TaskHourViewHolder>(TaskHourDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHourViewHolder {
        return TaskHourViewHolder(
            TaskHourBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskHourViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            taskName.text = item.name

            //преобразование времени в нормальный формат
            taskTime.text =
                "${SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(item.dateStart)}-${
                    SimpleDateFormat(
                        Constants.SIMPLE_HOUR_FROMAT
                    ).format(item.dateFinish)
                } : "

            //обработка нажатия на дело
            linearHour.setOnClickListener {
                onClick(item)
            }
        }
    }
}

class TaskHourViewHolder(val binding: TaskHourBinding) : RecyclerView.ViewHolder(binding.root)

class TaskHourDiffUtilCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }

}