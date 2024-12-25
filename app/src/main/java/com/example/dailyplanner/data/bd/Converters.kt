package com.example.dailyplanner.data.bd

import androidx.room.TypeConverter
import java.sql.Timestamp

// Конвертеры для преобразования Timestamp в Long и обратно
object Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Timestamp?): Long? {
        return date?.time
    }
}