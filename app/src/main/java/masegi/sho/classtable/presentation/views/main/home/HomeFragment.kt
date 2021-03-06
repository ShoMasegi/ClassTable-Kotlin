package masegi.sho.classtable.presentation.views.main.home


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
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


class HomeFragment : DaggerFragment(), OnTableItemClickListener, LifecycleOwner {


    // MARK: - Property

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ClassTableAdapter
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by lazy {

        ViewModelProviders.of(activity!!, viewModelFactory).get(HomeViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        adapter = ClassTableAdapter(LessonDataSource(listOf()), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.classTable.adapter = adapter
        viewModel.lessons.observe(this) { result ->

            when(result) {

                is Result.Success -> {

                    adapter.build(LessonDataSource(result.data))
                }
            }
        }
    }


    // MARK: - OnTableItemClickListener

    override fun onTableItemClick(view: View, item: Lesson?) {

        item?.let { navigationController.navigateToDetailActivity(it) }
    }

    override fun onTableItemLongClick(view: View, item: Lesson?, day: DayOfWeek, start: Int) {

        val popupMenu = PopupMenu(context, view)
        popupMenu.setOnMenuItemClickListener { menuItem ->

            when (menuItem?.itemId) {

                R.id.menu_add, R.id.menu_edit -> {

                    val lesson: Lesson = item ?: Lesson(tid = Prefs.tid, name = "", week = day, start = start)
                    navigationController.navigateToEditLessonActivity(lesson)
                    true
                }
                R.id.menu_delete -> {

                    adapter.getItemAt(day, start)?.let { showDeleteAlertDialog(it) }
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


    // MARK: - Private

    private fun showDeleteAlertDialog(lesson: Lesson) {

        val builder = AlertDialog.Builder(context!!).apply {

            setMessage(context.resources.getString(R.string.delete_class_question) + " : ${lesson.name}")
            setPositiveButton("DELETE") { _, _ -> viewModel.delete(lesson) }
            setNegativeButton("CANCEL") { _, _ -> }
        }
        builder.create().show()
    }


    companion object {

        fun newInstance(): HomeFragment = HomeFragment()
    }
}// Required empty public constructor
