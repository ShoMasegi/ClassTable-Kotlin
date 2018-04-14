package masegi.sho.classtable.presentation.views.main.todo

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableField
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
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


class TodoFragment : DaggerFragment(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentTodoBinding.inflate(inflater, container, false)
        binding.isEmpty = isEmpty
        todoListAdapter = TodoListAdapter(mutableListOf()) {

            navigationController.navigateToEditTaskActivity(it, it.theme)
        }
        binding.todoList.run {

            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
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

        val snackbar = Snackbar.make(
                binding.parentLayout,
                item.name + " removed from TODO.",
                Snackbar.LENGTH_SHORT
        ).apply {

            setAction("UNDO") {

                todoListAdapter.insertItem(item, position)
            }
            setActionTextColor(Color.GREEN)
            addCallback(object : Snackbar.Callback() {

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {

                    super.onDismissed(transientBottomBar, event)
                    if (event == DISMISS_EVENT_TIMEOUT) { viewModel.remove(item) }
                }
            })
        }
        snackbar.show()
    }


    // MARK: - RecyclerItemTouchHelperListener

    override fun onSwipe(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {

        showDeletedTaskSnackbar(todoListAdapter.tasks[position], position)
        todoListAdapter.removeItem(position)
    }

    companion object {

        val tag: String = TodoFragment.toString()
        fun newInstance(): TodoFragment = TodoFragment()
    }
}// Required empty public constructor
