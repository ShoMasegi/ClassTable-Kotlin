package masegi.sho.classtable.presentation.views.main.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.data.LessonDataSource
import masegi.sho.classtable.data.Prefs

import masegi.sho.classtable.databinding.FragmentHomeBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.adapter.ClassTableAdapter
import masegi.sho.classtable.presentation.adapter.OnTableItemClickListener


class HomeFragment : DaggerFragment(), OnTableItemClickListener {


    // MARK: - Property

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mAdapter: ClassTableAdapter


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater, container!!, false)
        mAdapter = ClassTableAdapter(LessonDataSource(listOf()), this)
        binding.classTable.apply {

            weeks = Prefs.weeks
            sectionCount = Prefs.dayLessonCount
            adapter = mAdapter
            build()
        }
        return binding.root
    }


    // MARK: - OnTableItemClickLis

    override fun onTableItemClick(item: Lesson?, day: DayOfWeek, start: Int) {
    }

    override fun onTableItemLongClick(item: Lesson?, day: DayOfWeek, start: Int) {
    }


    companion object {

        val tag: String = HomeFragment.toString()
        fun newInstance(): HomeFragment = HomeFragment()
    }
}// Required empty public constructor
