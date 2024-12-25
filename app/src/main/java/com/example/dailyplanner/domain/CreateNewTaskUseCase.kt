package com.example.dailyplanner.domain

import com.example.dailyplanner.data.CalendarRepository
import com.example.dailyplanner.entity.NewTaskDB
import javax.inject.Inject

class CreateNewTaskUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {
    // Создание новой задачи
    suspend fun createNewTask(task: NewTaskDB) {
        calendarRepository.addNewTask(task)
    }
}