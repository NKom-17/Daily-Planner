package com.example.dailyplanner.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

// Таблица задачи
@Entity(tableName = "TaskDB")
data class TaskDB(
    @PrimaryKey
    @ColumnInfo(name = "Id")
    override val id: Int? = null,
    @ColumnInfo(name = "date_start")
    override val dateStart: Timestamp,
    @ColumnInfo(name = "date_finish")
    override val dateFinish: Timestamp,
    @ColumnInfo(name = "name")
    override val name: String,
    @ColumnInfo(name = "description")
    override val description: String
) : Task