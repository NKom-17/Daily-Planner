package com.example.dailyplanner.entity

object Constants {
    // формат времени
    const val SIMPLE_HOUR_FROMAT = "HH:mm"

    // формат даты
    const val SIMPLE_DATE_FROMAT = "dd/MM/yyyy"

    // один час в миллисекндах
    const val HOUR = 3600000L

    // одна минута в миллисекндах
    const val MINUTE = 60000L

    // ошибки при создании новой задачи
    const val EMPTY_NAME_ERROR = "Пожалуйста, введите название задачи"
    const val EMPTY_DESCRIPTION_ERROR = "Пожалуйста, введите описание задачи"
    const val DATE_ERROR = "Пожалуйста, выберите дату или проверьте ее корректность"

}