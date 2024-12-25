package com.example.dailyplanner.data

import com.example.dailyplanner.data.bd.TaskDao
import com.example.dailyplanner.entity.Hour
import com.example.dailyplanner.entity.Constants
import com.example.dailyplanner.entity.NewTaskDB
import com.example.dailyplanner.entity.TaskDB
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CalendarRepository @Inject constructor(private val taskDao: TaskDao) {

    // Запрос списка всех задач на день
    suspend fun getListTask(day: Timestamp): List<Hour> {

        // заполняем список отсортированных по часам дел пустыми задачами
        val listHour = arrayListOf<Hour>()
        for (hour in 0..23) {
            listHour.add(Hour(hour * 3600000L, arrayListOf()))
        }

        // заполняем отсортированный список задачами по условию
        taskDao.getDayTasks(day).forEach { task ->
            listHour.forEach { hour ->
                val time = SimpleDateFormat(
                    Constants.SIMPLE_HOUR_FROMAT,
                    Locale.UK
                ).format(task.dateStart)
                val longTime = SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT, Locale.UK).parse(time)
                // проверка в какой час входит задача
                if (longTime != null) {
                    if (longTime.time >= hour.hour && longTime.time < hour.hour + Constants.HOUR) {
                        hour.tasks.add(task)
                    }
                }
            }
        }

        return listHour
    }

    // Получение задачи из бд
    suspend fun getTaskInfo(id: Int): TaskDB {
        return taskDao.getTaskInfo(id)
    }

    // Добавление новой задачи
    suspend fun addNewTask(task: NewTaskDB) {
        taskDao.addNewTask(task)
    }
}