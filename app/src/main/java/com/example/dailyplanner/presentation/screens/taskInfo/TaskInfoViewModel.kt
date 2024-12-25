package com.example.dailyplanner.presentation.screens.taskInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.GetTaskInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class TaskInfoViewModel @Inject constructor(private val getTaskInfoUseCase: GetTaskInfoUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<TaskInfoState>(TaskInfoState.Loading)
    val state = _state.asStateFlow()

    // получение задачи по id
    fun getTask(id: Int) {
        viewModelScope.launch {
            _state.value = TaskInfoState.Loading
            try {
                val task = getTaskInfoUseCase.getTaskInfo(id)
                _state.value = TaskInfoState.Success(task)
            } catch (e: Exception) {
                _state.value = TaskInfoState.Error("error receiving the task")
            }
        }
    }

}