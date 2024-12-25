package com.example.dailyplanner.entity

import java.sql.Timestamp

// Интерфейс задачи
interface Task {
    val id: Int?
    val dateStart: Timestamp
    val dateFinish: Timestamp
    val name: String
    val description: String
}