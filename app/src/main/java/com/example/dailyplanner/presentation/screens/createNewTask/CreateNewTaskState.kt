package com.example.dailyplanner.presentation.screens.createNewTask

sealed interface CreateNewTaskState {
    // Новая задача успешно создана
    data object Success : CreateNewTaskState

    // Начало создания задачи
    data object Start : CreateNewTaskState

    // Создание новой задачи
    data object Loading : CreateNewTaskState

    // Ошибка при создании новой задачи
    data class Error(val error: Map<String, Boolean>) : CreateNewTaskState
}