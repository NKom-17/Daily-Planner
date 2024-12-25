package com.example.dailyplanner.presentation.screens.taskInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class TaskInfoViewModelFactory @Inject constructor(private val taskInfoViewModel: TaskInfoViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return taskInfoViewModel as T
    }
}