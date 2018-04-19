package masegi.sho.classtable.presentation.views.setting


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.R
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.Pref

import masegi.sho.classtable.databinding.FragmentSettingBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.customview.NumberPickerDialog
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject


class SettingFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentSettingBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSettingBinding.inflate(inflater, container!!, false)
        binding.pref = Pref(name = "Sample", dayLessonCount = 5)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }


    // MARK: - Private

    private fun setupViews() {

        binding.selectWeek.setOnClickListener { showChooseDaysDialog() }
        binding.count.onClicked = { showDayLessonCountDialog() }
        binding.table.setOnClickListener { showChooseTableDialog() }
        binding.editTables.setOnClickListener {  }
        binding.editTime.setOnClickListener {  }
    }

    private fun showChooseDaysDialog() {

        val week = DayOfWeek.values()
        val defaultValue: BooleanArray = week.map { Prefs.weeks.contains(it) }.toBooleanArray()
        AlertDialog.Builder(context!!)
                .setMultiChoiceItems(
                        DayOfWeek.values().map { it.rawValue }.toTypedArray(),
                        defaultValue
                ) { _, which, isChecked -> defaultValue[which] = isChecked }
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
    }

    private fun showChooseTableDialog() {}

    private fun showDayLessonCountDialog() {

        NumberPickerDialog.Builder(context!!, R.style.AppTheme_AlertDialog).apply {

            mMinValue = 1
            mMaxValue = 8
            mTitle = resources.getString(R.string.dialog_count_classes)
            mDefaultValue = Prefs.dayLessonCount
            // TODO: create callback
        }.create().show()
    }

    companion object {

        internal fun newInstance(): SettingFragment = SettingFragment()
    }
}// Required empty public constructor
