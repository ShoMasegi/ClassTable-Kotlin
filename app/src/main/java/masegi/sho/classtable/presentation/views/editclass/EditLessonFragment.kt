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
import org.parceler.Parcels
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
        editLessonViewModel.lesson = Parcels.unwrap(arguments!!.getParcelable(EXTRA_LESSON))
        binding.lesson = editLessonViewModel.lesson
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
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
                    editLessonViewModel.lesson.theme = theme
                    binding.colorView.bindSquareThemeColor(theme)
                }
            }

            override fun onDismiss() {}
        })
        dialog.show(fragmentManager, "color_picker")
    }


    companion object {

        private const val EXTRA_LESSON = "EXTRA_LESSON"

        internal fun newInstance(lesson: Lesson): EditLessonFragment =
                EditLessonFragment().apply {

                    arguments = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                }
    }
}// Required empty public constructor
