package com.example.dailyplanner.data.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dailyplanner.entity.NewTaskDB
import com.example.dailyplanner.entity.TaskDB
import java.sql.Timestamp

@Dao
interface TaskDao {
    // Получение списка всех задач на день
    @Query("SELECT * FROM TaskDB Where :day<date_start AND date_start<:day+86400000 ")
    suspend fun getDayTasks(day: Timestamp): List<TaskDB>

    // Получение конткретной задачи
    @Query("SELECT * FROM TaskDB Where id=:id")
    suspend fun getTaskInfo(id: Int): TaskDB

    // Добавление новой задачи
    @Insert(entity = TaskDB::class)
    suspend fun addNewTask(dayTasks: NewTaskDB)
}