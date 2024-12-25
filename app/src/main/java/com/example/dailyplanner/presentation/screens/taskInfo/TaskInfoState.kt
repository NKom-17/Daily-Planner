package com.example.dailyplanner.presentation.screens.taskInfo

import com.example.dailyplanner.entity.TaskDB

sealed interface TaskInfoState {
    data class Success(val task: TaskDB) : TaskInfoState
    data object Loading : TaskInfoState
    data class Error(val error: String) : TaskInfoState
}