package masegi.sho.classtable.presentation.views.detail


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_detail.*
import masegi.sho.classtable.R
import masegi.sho.classtable.data.model.Memo

import masegi.sho.classtable.databinding.FragmentDetailBinding
import masegi.sho.classtable.databinding.ItemTodoBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.utli.CalendarUtil
import masegi.sho.classtable.utli.ext.observe
import org.parceler.Parcels
import javax.inject.Inject


class DetailFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentDetailBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }
    private lateinit var listAdapter: TaskAdapter


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel.lesson = Parcels.unwrap(arguments!!.getParcelable(EXTRA_LESSON))
        binding.lesson = viewModel.lesson
        listAdapter = TaskAdapter(arrayListOf(), binding.todoListParent, binding.todoList) {

            navigationController.navigateToEditTaskActivity(
                    it,
                    viewModel.lesson.theme,
                    viewModel.lesson.name
            )
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        observeValues()
        setupViews()
    }


    // MARK: - Private

    private fun observeValues() {

        viewModel.tasks.observe(this) { result ->

            when (result) {

                is Result.Success -> {

                    binding.tasks = result.data
                    listAdapter.tasks = result.data
                    listAdapter.notifyDataSetChanged()
                }
            }
        }
        viewModel.memo.observe(this) { result ->

            when (result) {

                is Result.Success -> binding.memo = result.data
            }
        }
    }

    private fun setupViews() {

        val fabMenu: FloatingActionMenu? = activity?.findViewById(R.id.fab_menu)

        activity?.findViewById<FloatingActionButton>(R.id.fab1)?.setOnClickListener {

            fabMenu?.close(true)
            navigationController.navigateToEditTaskActivity(
                    Task(tid = viewModel.lesson.tid, lid = viewModel.lesson.id),
                    viewModel.lesson.theme,
                    viewModel.lesson.name
            )
        }
        activity?.findViewById<FloatingActionButton>(R.id.fab2)?.setOnClickListener {

            fabMenu?.close(true)
            navigationController.navigateToEditMemoActivity(
                    binding.memo ?: Memo(viewModel.lesson.id, viewModel.lesson.tid)
            )
        }
    }


    // MARK: - ListViewAdapter

    private class TaskAdapter(
            var tasks: List<Task>,
            private val parent: ViewGroup,
            private val listView: ListView,
            var onTaskClick: ((Task) -> Unit)?
    ) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val binding: ItemTodoBinding = if (convertView != null) {

                val inflater = LayoutInflater.from(parent?.context)
                DataBindingUtil.inflate(inflater, R.layout.item_todo, parent, false)
            }
            else {

                DataBindingUtil.bind(convertView)
            }
            binding.task = getItem(position)
            binding.todoDueDate.text = CalendarUtil.calendarToSimpleDate(getItem(position).dueAt)
            binding.root.setOnClickListener { onTaskClick?.invoke(getItem(position)) }
            return binding.root
        }

        override fun getItem(position: Int): Task = tasks[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getCount(): Int = tasks.size

        override fun notifyDataSetChanged() {

            super.notifyDataSetChanged()
            val itemCount = if (count > 3) 3 else count
            if (count in 1..3) {

                val item = getView(0, null, parent)
                item.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                val params = listView.layoutParams
                params.height = itemCount * item.measuredHeight
                listView.layoutParams = params
            }
            else {

                val params = listView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                listView.layoutParams = params
            }
        }
    }

    companion object {

        private const val EXTRA_LESSON = "EXTRA_LESSON"

        internal fun newInstance(lesson: Lesson): DetailFragment =
                DetailFragment().apply {

                    arguments = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                }
    }

}// Required empty public constructor
