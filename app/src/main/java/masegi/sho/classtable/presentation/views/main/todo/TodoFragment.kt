package masegi.sho.classtable.presentation.views.main.todo

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.ObservableField
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.databinding.FragmentTodoBinding
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.presentation.adapter.TodoListAdapter
import masegi.sho.classtable.presentation.common.binding.bindSpinnerData
import masegi.sho.classtable.presentation.helper.RecyclerItemTouchHelper
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject


class TodoFragment : DaggerFragment(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, LifecycleOwner {


    // MARK: - Property

    private lateinit var binding: FragmentTodoBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TodoViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(TodoViewModel::class.java)
    }
    private lateinit var todoListAdapter: TodoListAdapter
    private val isEmpty: ObservableField<Boolean> = ObservableField(false)


    // MARK: - Fragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentTodoBinding.inflate(inflater, container, false)
        binding.isEmpty = isEmpty
        todoListAdapter = TodoListAdapter(mutableListOf()) {

            navigationController.navigateToEditTaskActivity(it, it.theme)
        }
        binding.todoList.run {

            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, LinearLayout.VERTICAL))
            adapter = todoListAdapter
        }
        val itemTouchHelperCallback = RecyclerItemTouchHelper(
                ItemTouchHelper.LEFT,
                ItemTouchHelper.LEFT,
                this
        )
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.todoList)
        observeValues()
        return binding.root
    }


    // MARK: - Private

    private fun observeValues() {

        viewModel.tasks.observe(this) { result ->

            when (result) {

                is Result.Success -> {

                    isEmpty.set(result.data.isEmpty())
                    todoListAdapter.notifyDataSetChanged(result.data as MutableList<Task>)
                }
                is Result.Failure -> isEmpty.set(false)
            }
        }
    }

    private fun showDeletedTaskSnackbar(item: Task, position: Int) {

        val snackbar = com.google.android.material.snackbar.Snackbar.make(
                binding.parentLayout,
                item.name + " removed from TODO.",
                com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).apply {

            setAction("UNDO") {

                todoListAdapter.insertItem(item, position)
            }
            setActionTextColor(Color.GREEN)
            addCallback(object : com.google.android.material.snackbar.Snackbar.Callback() {

                override fun onDismissed(transientBottomBar: com.google.android.material.snackbar.Snackbar?, event: Int) {

                    super.onDismissed(transientBottomBar, event)
                    if (event == DISMISS_EVENT_TIMEOUT) { viewModel.remove(item) }
                }
            })
        }
        snackbar.show()
    }


    // MARK: - RecyclerItemTouchHelperListener

    override fun onSwipe(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder?, direction: Int, position: Int) {

        showDeletedTaskSnackbar(todoListAdapter.tasks[position], position)
        todoListAdapter.removeItem(position)
    }

    companion object {

        val tag: String = TodoFragment.toString()
        fun newInstance(): TodoFragment = TodoFragment()
    }
}// Required empty public constructor
