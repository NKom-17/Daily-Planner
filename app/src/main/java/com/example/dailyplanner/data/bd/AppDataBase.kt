package com.example.dailyplanner.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dailyplanner.entity.TaskDB

@Database(entities = [TaskDB::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    // создание экзампляра базы данных
    abstract fun createDao(): TaskDao
}