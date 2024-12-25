package com.example.dailyplanner.domain

import com.example.dailyplanner.data.CalendarRepository
import com.example.dailyplanner.entity.TaskDB
import javax.inject.Inject

class GetTaskInfoUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {
    // Запрос конткретной задачи по id
    suspend fun getTaskInfo(id: Int): TaskDB {
        return calendarRepository.getTaskInfo(id)
    }
}