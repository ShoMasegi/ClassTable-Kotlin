package masegi.sho.classtable.presentation.views.editlesson


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.R

import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.databinding.FragmentEditLessonBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.SaveResult
import masegi.sho.classtable.presentation.customview.NumberPickerDialog
import masegi.sho.classtable.utli.ext.observe
import org.parceler.Parcels
import javax.inject.Inject
import android.view.WindowManager
import android.support.v4.content.ContextCompat
import masegi.sho.classtable.data.model.AttendType
import masegi.sho.classtable.kotlin.data.model.ThemeColor


class EditLessonFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentEditLessonBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditLessonViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(EditLessonViewModel::class.java)
    }


    // MARK: - Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentEditLessonBinding.inflate(inflater, container!!, false)
        viewModel.lesson = Parcels.unwrap(arguments!!.getParcelable(EXTRA_LESSON))
        binding.lesson = viewModel.lesson
        changeThemeColor(viewModel.lesson.theme)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel.isSaveSuccess.observe(this) {

            when (it) {

                is SaveResult.Success -> activity?.finish()

                is SaveResult.Failure -> {

                    AlertDialog.Builder(this!!.context!!)
                            .setTitle("Error")
                            .setPositiveButton(android.R.string.ok, null)
                            .setMessage(it.errorMessage ?: R.string.error_default)
                            .create()
                            .show()
                }
            }
        }
        setupViews()
    }


    // MARK: - Private

    private fun setupViews() {

        binding.saveButton.setOnClickListener { viewModel.save() }
        binding.attendParent.setOnClickListener { showNumberPickerDialog(AttendType.ATTEND) }
        binding.lateParent.setOnClickListener { showNumberPickerDialog(AttendType.LATE) }
        binding.absentParent.setOnClickListener { showNumberPickerDialog(AttendType.ABSENT) }
        binding.closeButton.setOnClickListener { activity?.onBackPressed() }
        binding.dayOfWeekRow.setOnClickListener {

            AlertDialog.Builder(this!!.context!!)
                    .setTitle(R.string.day_of_week)
                    .setItems(Prefs.weeks.map { it.rawValue }.toTypedArray()) { _, index ->

                        viewModel.lesson.week = Prefs.weeks[index]
                        viewModel.lesson.notifyChange()
                    }
                    .create()
                    .show()
        }
        binding.startTimeRow.setOnClickListener {

            NumberPickerDialog.Builder(this!!.context!!).apply {

                mMinValue = 1
                mMaxValue = Prefs.dayLessonCount
                mTitle = context.resources.getString(R.string.start_time)
                mDefaultValue = viewModel.lesson.start
                mIsHiddenTimesLabel = true
                mCallback = {

                    viewModel.lesson.start = it
                    viewModel.lesson.notifyChange()
                }
            }.create().show()
        }
        binding.sectionRow.setOnClickListener {

            NumberPickerDialog.Builder(this!!.context!!).apply {

                mMinValue = 1
                mMaxValue = Prefs.dayLessonCount
                mTitle = context.resources.getString(R.string.section)
                mDefaultValue = viewModel.lesson.section
                mIsHiddenTimesLabel = true
                mCallback = {

                    viewModel.lesson.section = it
                    viewModel.lesson.notifyChange()
                }
            }.create().show()
        }
        binding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            val factor = (-verticalOffset).toFloat() / appBarLayout.totalScrollRange.toFloat()
            binding.toolbarTextColorFactor = factor
        }
        binding.colorRow.setOnChangedListener { changeThemeColor(it) }
    }

    private fun showNumberPickerDialog(type: AttendType) {

        val defValue: Int
        val callback: (Int) -> Unit
        val lesson = viewModel.lesson
        when (type) {

            AttendType.ATTEND -> {

                defValue = lesson.attendance.attend
                callback = {

                    lesson.attendance.attend = it
                    lesson.notifyChange()
                }
            }
            AttendType.LATE -> {

                defValue = lesson.attendance.late
                callback = {

                    lesson.attendance.late = it
                    lesson.notifyChange()
                }
            }
            AttendType.ABSENT -> {

                defValue = lesson.attendance.absence
                callback = {

                    lesson.attendance.absence = it
                    lesson.notifyChange()
                }
            }
        }
        NumberPickerDialog.Builder(this!!.context!!).apply {

            mMinValue = 0
            mMaxValue = 15
            mTitle = type.title
            mDefaultValue = defValue
            mCallback = callback
        }.create().show()
    }

    private fun changeThemeColor(theme: ThemeColor) {

        val window = activity?.window
        window?.let {

            it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val colorPrimary: Int
            val colorPrimaryDark: Int
            if (theme == ThemeColor.DEFAULT) {
                colorPrimary = ContextCompat.getColor(context!!, R.color.colorPrimary)
                colorPrimaryDark = ContextCompat.getColor(context!!, R.color.colorPrimaryDark)
            } else {
                colorPrimary = ContextCompat.getColor(context!!, theme.primaryColorResId)
                colorPrimaryDark = ContextCompat.getColor(context!!, theme.primaryColorDarkResId)
            }
            it.statusBarColor = colorPrimaryDark
            binding.appBar.setBackgroundColor(colorPrimary)
        }
    }


    companion object {

        private const val EXTRA_LESSON = "EXTRA_LESSON"

        internal fun newInstance(lesson: Lesson): EditLessonFragment =
                EditLessonFragment().apply {

                    arguments = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                }
    }
}// Required empty public constructor
