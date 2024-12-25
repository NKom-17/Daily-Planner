package com.example.dailyplanner.domain

import com.example.dailyplanner.data.CalendarRepository
import com.example.dailyplanner.entity.Hour
import java.sql.Timestamp
import javax.inject.Inject

class GetListTaskUseCase @Inject constructor(private val calendarRepository: CalendarRepository) {

    // Получение списка всех задач на день
    suspend fun getListTask(day: Timestamp): List<Hour> {
        return calendarRepository.getListTask(day)
    }
}