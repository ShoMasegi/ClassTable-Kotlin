package masegi.sho.classtable.presentation.views.main.today


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.Pref

import masegi.sho.classtable.databinding.FragmentTodayBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import java.util.*
import javax.inject.Inject


class TodayFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentTodayBinding
    private lateinit var viewPagerAdapter: LessonsViewPagerAdapter
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TodayViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(TodayViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentTodayBinding.inflate(inflater, container, false)
        viewPagerAdapter = LessonsViewPagerAdapter(childFragmentManager)
        binding.viewPager.adapter = viewPagerAdapter
        viewPagerAdapter.setDaysOfWeek(Prefs.weeks)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val today = DayOfWeek.getValue(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1)
        binding.viewPager.currentItem = Prefs.weeks.indexOfFirst { it == today }
    }

    companion object {

        val tag: String = TodayFragment.toString()
        fun newInstance(): TodayFragment = TodayFragment()
    }
}// Required empty public constructor

class LessonsViewPagerAdapter(
        fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    private val tabs = arrayListOf<Tab>()

    internal fun setDaysOfWeek(days: List<DayOfWeek>) {

        if (days != tabs.map { it.day }) {

            val newTabs: List<Tab> = days.map { Tab(it) }.toMutableList()
            setupTabsIfNeeded(newTabs)
        }
    }

    data class Tab(val day: DayOfWeek) {

        val title: String = day.rawValue.substring(0..2)
        val shortTitle: String = day.rawValue.first().toString()
        val fragment: Fragment
            get() = LessonListFragment.newInstance(day)
    }

    override fun getItem(position: Int): Fragment = tabs[position].fragment

    override fun getCount(): Int = tabs.size

    override fun getPageTitle(position: Int): CharSequence? = if (tabs.size > 5) {

        tabs[position].shortTitle
    }
    else {

        tabs[position].title
    }

    private fun setupTabsIfNeeded(otherTabs: List<Tab>) {

        if (tabs.isNotEmpty() && tabs == otherTabs) { return }

        tabs.clear()
        tabs.addAll(otherTabs)
        notifyDataSetChanged()
    }

}
