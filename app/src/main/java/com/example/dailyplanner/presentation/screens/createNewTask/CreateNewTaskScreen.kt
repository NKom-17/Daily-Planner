package com.example.dailyplanner.presentation.screens.createNewTask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dailyplanner.databinding.ScreenCreateNewTaskBinding
import com.example.dailyplanner.entity.Constants
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewTaskScreen : Fragment() {

    companion object {
        fun newInstance() = CreateNewTaskScreen()
    }

    @Inject
    lateinit var factoryCreateNewTaskViewModel: CreateNewTaskViewModelFactory

    private lateinit var binding: ScreenCreateNewTaskBinding
    private val viewModel: CreateNewTaskViewModel by viewModels { factoryCreateNewTaskViewModel }

    private var timeStart = 0L //время начала задачи
    private var timeEnd = 0L //время окончания задачи
    private var timeDate = 0L //дата задачи
    private var name = "" //название задачи
    private var description = "" //описание задачи

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScreenCreateNewTaskBinding.inflate(inflater)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                when (it) {
                    is CreateNewTaskState.Error -> {
                        // обработка ошибок
                        if (it.error["NAME"] == false) {
                            binding.nameLayout.error = Constants.EMPTY_NAME_ERROR
                        } else {
                            binding.nameLayout.error = null
                        }
                        if (it.error["DESCRIPTION"] == false) {
                            binding.descriptionLayout.error = Constants.EMPTY_DESCRIPTION_ERROR
                        } else {
                            binding.descriptionLayout.error = null
                        }
                        if (it.error["Time"] == false) {
                            binding.errorTime.text = Constants.DATE_ERROR
                            binding.errorTime.visibility = View.VISIBLE
                        } else {
                            binding.errorTime.visibility = View.GONE
                        }
                    }

                    CreateNewTaskState.Loading -> {

                    }

                    CreateNewTaskState.Success -> {
                        // убираем ошибки и возвращаемся на главный экран
                        binding.nameLayout.error = null
                        binding.descriptionLayout.error = null
                        binding.errorTime.visibility = View.GONE
                        findNavController().popBackStack()
                    }

                    CreateNewTaskState.Start -> {

                    }
                }
            }
        }


        // вызов и обработка календаря
        binding.btnDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                binding.txtDate.text = SimpleDateFormat(Constants.SIMPLE_DATE_FROMAT).format(it)
                timeDate = it
            }

            datePicker.show(childFragmentManager, "date")
        }

        // вызов и обработка часов для timeStart
        binding.btnStartTime.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build().apply {
                    addOnPositiveButtonClickListener {
                        val timeStartPicker =
                            this.hour * Constants.HOUR + this.minute * Constants.MINUTE - clockGMT()
                        binding.txtStartTime.text =
                            SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(timeStartPicker)
                        timeStart = timeStartPicker
                    }
                }
            picker.show(childFragmentManager, "Start Time")
        }

        // вызов и обработка часов для timeEnd
        binding.btnFinishTime.setOnClickListener {
            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build().apply {
                    addOnPositiveButtonClickListener {
                        val timeEndPicker =
                            this.hour * Constants.HOUR + this.minute * Constants.MINUTE - clockGMT()
                        binding.txtEndTime.text =
                            SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(timeEndPicker)
                        timeEnd = timeEndPicker
                    }
                }
            picker.show(childFragmentManager, "Finish Time")
        }


        binding.btnCreat.setOnClickListener {
            name = binding.nameEdit.text.toString()
            description = binding.descriptionEdit.text.toString()
            // вызов метода viewModel для обработки вводимых данных и последующего сохранения
            viewModel.addTask(name, description, timeStart, timeEnd, timeDate)
        }


        binding.btnBackAdd.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun clockGMT(): Long {
        val mCalendar: Calendar = GregorianCalendar()
        val mTimeZone = mCalendar.timeZone
        val mGMTOffset = mTimeZone.rawOffset
        return TimeUnit.MINUTES.convert(
            mGMTOffset.toLong(),
            TimeUnit.MILLISECONDS
        ) * Constants.MINUTE
    }

}