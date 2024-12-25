package com.example.dailyplanner.presentation.screens.calendar

import com.example.dailyplanner.entity.Hour

sealed interface CalendarState {
    data class Success(val datTasks: List<Hour>) : CalendarState
    data object Loading : CalendarState
    data class Error(val error: String) : CalendarState
}