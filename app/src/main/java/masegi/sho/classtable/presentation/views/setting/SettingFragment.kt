package masegi.sho.classtable.presentation.views.setting


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
import masegi.sho.classtable.data.model.PrefEntity

import masegi.sho.classtable.databinding.FragmentSettingBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.common.KotPrefs
import masegi.sho.classtable.presentation.customview.NumberPickerDialog
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject


class SettingFragment : DaggerFragment() {


    // MARK: - Property

    private var prefs: List<PrefEntity> = listOf()
    private var pref = PrefEntity()
        set(value) {

            field = value
            binding.pref = value
        }
    private lateinit var binding: FragmentSettingBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSettingBinding.inflate(inflater, container!!, false)
        binding.pref = PrefEntity()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel.prefs.observe(this) { result ->

            when (result) {

                is Result.Success -> {

                    prefs = result.data
                    result.data.firstOrNull { it.tid == KotPrefs.tid }?.let { pref = it }
                }
            }
        }
        setupViews()
    }

    override fun onPause() {

        super.onPause()
        Prefs.sync(pref)
        KotPrefs.tid = pref.tid
    }


    // MARK: - Private

    private fun setupViews() {

        binding.selectWeek.setOnClickListener { showChooseDaysDialog() }
        binding.count.onClicked = { showDayLessonCountDialog() }
        binding.table.onClicked = { showChooseTableDialog() }
        binding.editTables.setOnClickListener { navigationController.navigateToEditTableActivity() }
        binding.editTime.setOnClickListener {  }
    }

    private fun showChooseDaysDialog() {

        val week = DayOfWeek.values()
        val defaultValue: BooleanArray = week.map { pref.weeks.contains(it) }.toBooleanArray()
        AlertDialog.Builder(context!!)
                .setMultiChoiceItems(
                        DayOfWeek.values().map { it.rawValue }.toTypedArray(),
                        defaultValue
                ) { _, which, isChecked -> defaultValue[which] = isChecked }
                .setPositiveButton(android.R.string.ok) { _, _ ->

                    val days: MutableList<DayOfWeek> = mutableListOf()
                    defaultValue.forEachIndexed { i, bool ->

                        if (bool) days.add(DayOfWeek.getValue(i))
                    }
                    pref.weeks = days
                    viewModel.insert(pref)
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
    }

    private fun showChooseTableDialog() {

        val tids: List<Int> = prefs.map { it.tid }
        val names: List<String> = prefs.map { it.name }
        var checkId: Int = -1
        AlertDialog.Builder(context!!)
                .setSingleChoiceItems(
                        names.toTypedArray(),
                        tids.indexOf(pref.tid),
                        { _, which -> checkId = tids[which] }
                )
                .setPositiveButton(android.R.string.ok) { _, _ ->

                    prefs.firstOrNull { it.tid == checkId }?.let { pref = it }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setTitle(R.string.dialog_choose_table)
                .show()
    }

    private fun showDayLessonCountDialog() {

        NumberPickerDialog.Builder(context!!, R.style.AppTheme_AlertDialog).apply {

            mMinValue = 1
            mMaxValue = 8
            mTitle = resources.getString(R.string.dialog_count_classes)
            mDefaultValue = pref.dayLessonCount
            mCallback = {

                pref.dayLessonCount = it
                viewModel.insert(pref)
            }
        }.create().show()
    }

    companion object {

        internal fun newInstance(): SettingFragment = SettingFragment()
    }
}// Required empty public constructor
