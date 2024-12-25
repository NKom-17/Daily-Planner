package com.example.dailyplanner.presentation.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailyplanner.domain.CreateNewTaskUseCase
import com.example.dailyplanner.domain.GetListTaskUseCase
import com.example.dailyplanner.entity.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getListTaskUseCase: GetListTaskUseCase,
    private val createNewTaskUseCase: CreateNewTaskUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<CalendarState>(CalendarState.Success(emptyList()))
    val state = _state.asStateFlow()

    fun getDayTasks(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            _state.value = CalendarState.Loading
            // подготовка данных для отправки запроса
            val simpleDateFormat = SimpleDateFormat(Constants.SIMPLE_DATE_FROMAT)
            val date = simpleDateFormat.parse("$day/${month + 1}/$year")
            try {
                // получение списка задач по часом на конкретный день
                val listTasks = getListTaskUseCase.getListTask(Timestamp(date!!.time))
                _state.value = CalendarState.Success(listTasks)
            } catch (e: Exception) {
                _state.value = CalendarState.Error("error")
            }
        }
    }
}