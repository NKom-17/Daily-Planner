package com.example.dailyplanner.presentation.screens.createNewTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class CreateNewTaskViewModelFactory @Inject constructor(private val createNewTaskViewModel: CreateNewTaskViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return createNewTaskViewModel as T
    }
}