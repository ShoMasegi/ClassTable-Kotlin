package masegi.sho.classtable.presentation.views.detail


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.clans.fab.FloatingActionButton
import com.github.clans.fab.FloatingActionMenu
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.R
import masegi.sho.classtable.data.model.Memo

import masegi.sho.classtable.databinding.FragmentDetailBinding
import masegi.sho.classtable.databinding.ItemTodoBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
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
    private lateinit var adapter: TasksAdapter


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel.lesson = Parcels.unwrap(arguments!!.getParcelable(EXTRA_LESSON))
        binding.lesson = viewModel.lesson
        adapter = TasksAdapter(arrayListOf()) {

            navigationController.navigateToEditTaskActivity(
                    it,
                    viewModel.lesson.theme
            )
        }
        binding.todoList.adapter = adapter
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
                    adapter.tasks = result.data
                    adapter.notifyDataSetChanged()
                    resizeListView(result.data.size)
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
                    Task(lesson = viewModel.lesson),
                    viewModel.lesson.theme
            )
        }
        activity?.findViewById<FloatingActionButton>(R.id.fab2)?.setOnClickListener {

            fabMenu?.close(true)
            navigationController.navigateToEditMemoActivity(binding.memo, viewModel.lesson)
        }
    }

    private fun resizeListView(itemCount: Int) {

        val count = if (itemCount > 3) 3 else itemCount
        val itemHeight: Float = resources.displayMetrics.density * 42
        val params = binding.todoList.layoutParams
        params.height = count * itemHeight.toInt()
        binding.todoList.layoutParams = params
    }


    // MARK: - ListViewAdapter

    private class TasksAdapter(
            var tasks: List<Task>,
            var onTaskClick: ((Task) -> Unit)?
    ) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

            val inflater = LayoutInflater.from(parent?.context)
            val binding: ItemTodoBinding = ItemTodoBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

            val task = tasks[position]
            holder?.binding?.task = task
            holder?.binding?.root?.setOnClickListener { onTaskClick?.invoke(task) }
        }

        override fun getItemCount(): Int = if (tasks.size > 3) 3 else tasks.size

        class ViewHolder(var binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)
    }

    companion object {

        private const val EXTRA_LESSON = "EXTRA_LESSON"

        internal fun newInstance(lesson: Lesson): DetailFragment =
                DetailFragment().apply {

                    arguments = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                }
    }

}// Required empty public constructor
