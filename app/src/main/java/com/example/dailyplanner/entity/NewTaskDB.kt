package com.example.dailyplanner.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.sql.Timestamp

// Класс для добавления новых задач в базу данных
data class NewTaskDB(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    val id: Int? = null,
    @ColumnInfo(name = "date_start")
    val dateStart: Timestamp,
    @ColumnInfo(name = "date_finish")
    val dateFinish: Timestamp,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String
)