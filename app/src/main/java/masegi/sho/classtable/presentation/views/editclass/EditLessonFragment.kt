package masegi.sho.classtable.presentation.views.editclass


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import dagger.android.support.DaggerFragment

import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.FragmentEditLessonBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject


class EditLessonFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentEditLessonBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val editLessonViewModel: EditLessonViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(EditLessonViewModel::class.java)
    }


    // MARK: - Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentEditLessonBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val day = DayOfWeek.getWeek(arguments!!.getInt(EXTRA_LESSON_DAY))
        val start = arguments!!.getInt(EXTRA_LESSON_START)
        editLessonViewModel.day = day
        editLessonViewModel.start = start
        editLessonViewModel.lesson.observe(this) { result ->

            when(result) {

                is Result.Success -> { binding.lesson = result.data }
                is Result.Failure -> {

                    binding.lesson = Lesson(name = "", week = day, start = start)
                }
            }
        }
    }


    companion object {

        private const val EXTRA_LESSON_DAY = "EXTRA_LESSON_DAY"
        private const val EXTRA_LESSON_START = "EXTRA_LESSON_START"

        internal fun newInstance(day: DayOfWeek, start: Int): EditLessonFragment =
                EditLessonFragment().apply {

                    arguments = bundleOf(
                            EXTRA_LESSON_DAY to day.ordinal,
                            EXTRA_LESSON_START to start
                    )
                }
    }
}// Required empty public constructor
