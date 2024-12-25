package com.example.dailyplanner.presentation.screens.taskInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dailyplanner.databinding.ScreenTaskInfoBinding
import com.example.dailyplanner.entity.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class TaskInfoScreen : Fragment() {

    companion object {
        fun newInstance() = TaskInfoScreen()
    }

    @Inject
    lateinit var taskInfoViewModelFactory: TaskInfoViewModelFactory

    private lateinit var binding: ScreenTaskInfoBinding
    private val viewModel: TaskInfoViewModel by viewModels { taskInfoViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // получение выбранной задачи
        arguments.let {
            viewModel.getTask(it?.getInt("ID") ?: 0)
        }

        binding = ScreenTaskInfoBinding.inflate(inflater)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                // обработка состояния
                when (it) {
                    is TaskInfoState.Error -> {
                        // вывод ошибки
                        binding.description.text = it.error
                    }

                    TaskInfoState.Loading -> {

                    }

                    is TaskInfoState.Success -> {
                        // показ информации о задаче
                        binding.description.text = it.task.description
                        binding.timeFull.text =
                            "${SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(it.task.dateStart)}-${
                                SimpleDateFormat(Constants.SIMPLE_HOUR_FROMAT).format(it.task.dateFinish)
                            }"
                        binding.date.text =
                            SimpleDateFormat(Constants.SIMPLE_DATE_FROMAT).format(it.task.dateStart)
                        binding.name.text = it.task.name
                    }
                }
            }
        }

        // возврат на главный экран
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}