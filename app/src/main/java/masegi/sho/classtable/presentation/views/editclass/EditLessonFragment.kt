package masegi.sho.classtable.presentation.views.editclass


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.os.bundleOf
import com.android.databinding.library.baseAdapters.BR
import dagger.android.support.DaggerFragment

import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.databinding.FragmentEditLessonBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.binding.bindSquareThemeColor
import masegi.sho.classtable.presentation.common.binding.bindThemeColor
import masegi.sho.classtable.presentation.customview.ColorPickerDialog.ColorPickerDialog
import masegi.sho.classtable.utli.ext.observe
import java.util.ArrayList
import javax.inject.Inject


class EditLessonFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentEditLessonBinding
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

                is Result.Success -> {

                    binding.lesson = result.data
                }
                is Result.Failure, is Result.InProgress -> {

                    binding.lesson = Lesson(name = "", week = day, start = start)
                }
            }
        }
        setupViews()
    }


    // MARK: - Private

    private fun setupViews() {

        setupSpinner()
        binding.saveButton.setOnClickListener {

            binding.lesson?.let { editLessonViewModel.save(it) }
        }
        binding.cancelButton.setOnClickListener { activity?.onBackPressed() }
        binding.colorView.setOnClickListener { showColorPickerDialog() }
    }

    private fun setupSpinner() {

        val adapter: ArrayAdapter<Int> = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item)
        for (i in 1 until Prefs.dayLessonCount + 1) { adapter.add(i) }
        binding.sectionSpinner.adapter = adapter
    }

    private fun showColorPickerDialog() {

        val dialog: ColorPickerDialog = ColorPickerDialog.newInstance(
                ColorPickerDialog.SELECTION_SINGLE,
                ThemeColor.primaryColorResIdList,
                4,
                ColorPickerDialog.SIZE_SMALL
        )
        dialog.setOnDialogButtonListener(object : ColorPickerDialog.OnDialogButtonListener {

            override fun onDonePressed(mSelectedColors: ArrayList<Int>) {

                if (mSelectedColors.size > 0) {

                    val theme = ThemeColor.getByPrimaryColorResId(mSelectedColors[0])
                    binding.lesson?.theme = theme
                    binding.colorView.bindSquareThemeColor(theme)
                }
            }

            override fun onDismiss() {}
        })
        dialog.show(fragmentManager, "color_picker")
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
