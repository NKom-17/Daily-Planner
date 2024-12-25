package com.example.dailyplanner.presentation.screens.createNewTask

import androidx.collection.arrayMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.CreateNewTaskUseCase
import com.example.dailyplanner.entity.NewTaskDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

class CreateNewTaskViewModel @Inject constructor(private val createNewTaskUseCase: CreateNewTaskUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<CreateNewTaskState>(CreateNewTaskState.Start)
    val state = _state.asStateFlow()

    fun addTask(name: String, descrption: String, timeStart: Long, timeEnd: Long, timeDate: Long) {
        viewModelScope.launch {
            _state.value = CreateNewTaskState.Loading

            // словарь для записи ошибок
            val errorMap = arrayMapOf(
                Pair("NAME", true),
                Pair("DESCRIPTION", true),
                Pair("Time", true)
            )

            //п роверка имени
            if (name == "") {
                errorMap["NAME"] = false
            }

            // проверка описания
            if (descrption == "") {
                errorMap["DESCRIPTION"] = false
            }

            // проверка временя (время окончания не раньше времени начала)
            if (timeStart > timeEnd || timeStart == 0L || timeEnd == 0L || timeDate == 0L) {
                errorMap["Time"] = false
            }

            // обработка результатов проверок и смена состояния
            if (errorMap.values.all { it }) {
                val task = NewTaskDB(
                    null,
                    Timestamp(timeDate + timeStart),
                    Timestamp(timeDate + timeEnd),
                    name,
                    descrption
                )

                createNewTaskUseCase.createNewTask(task)
                _state.value = CreateNewTaskState.Success

            } else {
                _state.value = CreateNewTaskState.Error(errorMap)
            }
        }
    }

}