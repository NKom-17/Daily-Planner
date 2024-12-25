package com.example.dailyplanner.presentation.screens.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dailyplanner.R
import com.example.dailyplanner.databinding.ScreenMainBinding
import com.example.dailyplanner.entity.Task
import com.example.dailyplanner.presentation.screens.calendar.adapters.TaskDayAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var mainFactory: MainViewModelFactory

    private lateinit var binding: ScreenMainBinding
    private val viewModel: MainViewModel by viewModels { mainFactory }
    private val adapter = TaskDayAdapter { task -> onClick(task) }

    // создание календаря для обработки даты с включением экрана и отображения задач
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScreenMainBinding.inflate(inflater)
        // загрузка задач текущего дня при запуске
        viewModel.getDayTasks(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // обработка даты при клике на день в календаре и отправка во viewModel
        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            viewModel.getDayTasks(year, month, day)
        }


        binding.rcDescription.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                // обработка состояния
                when (it) {
                    is CalendarState.Error -> {
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    }

                    CalendarState.Loading -> {

                    }

                    is CalendarState.Success -> {
                        adapter.submitList(it.datTasks)
                    }
                }
            }
        }

        // переход на экран добавления новой задачи
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreen_to_createNewTaskScreen)
        }

        return binding.root
    }

    // переход на экран с описанием задачи
    private fun onClick(taskDB: Task) {
        val bundle = Bundle()
        bundle.putInt("ID", taskDB.id!!)
        findNavController().navigate(R.id.action_mainScreen_to_taskInfoScreen, bundle)
    }

}