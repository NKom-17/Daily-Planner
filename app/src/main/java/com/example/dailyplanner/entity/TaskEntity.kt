package com.example.dailyplanner.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Timestamp

// Сущность задачи
@JsonClass(generateAdapter = true)
data class TaskEntity(
    @Json(name = "Id")
    override val id: Int,
    @Json(name = "date_start")
    override val dateStart: Timestamp,
    @Json(name = "date_finish")
    override val dateFinish: Timestamp,
    @Json(name = "name")
    override val name: String,
    @Json(name = "description")
    override val description: String
) : Task