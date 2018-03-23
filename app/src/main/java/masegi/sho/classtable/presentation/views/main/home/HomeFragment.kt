package masegi.sho.classtable.presentation.views.main.home


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.R
import masegi.sho.classtable.data.LessonDataSource
import masegi.sho.classtable.data.Prefs

import masegi.sho.classtable.databinding.FragmentHomeBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.adapter.ClassTableAdapter
import masegi.sho.classtable.presentation.adapter.OnTableItemClickListener
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject


class HomeFragment : DaggerFragment(), OnTableItemClickListener {


    // MARK: - Property

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ClassTableAdapter
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val homeViewModel: HomeViewModel by lazy {

        ViewModelProviders.of(activity!!, viewModelFactory).get(HomeViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater, container!!, false)
        adapter = ClassTableAdapter(LessonDataSource(listOf()), this)
        binding.classTable.apply {

            weeks = Prefs.weeks
            sectionCount = Prefs.dayLessonCount
            adapter = this@HomeFragment.adapter
            build()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        homeViewModel.lessons.observe(this) { result ->

            when(result) {

                is Result.Success -> {

                    adapter.datasetChanged(LessonDataSource(result.data))
                }
            }
        }
    }


    // MARK: - OnTableItemClickListener

    override fun onTableItemClick(view: View, item: Lesson?) {

        if (item != null) {

            navigationController.navigateToDetailActivity(item.id)
        }
    }

    override fun onTableItemLongClick(view: View, item: Lesson?, day: DayOfWeek, start: Int) {

        val popupMenu = PopupMenu(context, view)
        popupMenu.setOnMenuItemClickListener { menuItem ->

            when (menuItem?.itemId) {

                R.id.menu_add, R.id.menu_edit -> {

                    navigationController.navigateToEditLessonActivity(day, start)
                    true
                }
                R.id.menu_delete -> {

                    adapter.getItemAt(day, start)?.let { homeViewModel.delete(it) }
                    true
                }
                else -> false
            }
        }
        if (item != null) {

            popupMenu.menuInflater.inflate(R.menu.menu_exist_lesson, popupMenu.menu)
        }
        else {

            popupMenu.menuInflater.inflate(R.menu.menu_lesson, popupMenu.menu)
        }
        popupMenu.show()
    }


    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
}// Required empty public constructor
