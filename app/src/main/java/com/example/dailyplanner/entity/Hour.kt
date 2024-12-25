package com.example.dailyplanner.entity

// Класс для массива сортировки задач по часам и отправки в RecycleView
data class Hour(
    val hour: Long,
    val tasks: ArrayList<Task>
)