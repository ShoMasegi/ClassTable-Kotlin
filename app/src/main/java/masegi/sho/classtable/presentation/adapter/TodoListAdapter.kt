package masegi.sho.classtable.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import masegi.sho.classtable.databinding.ItemTodoHeaderBinding
import masegi.sho.classtable.databinding.ItemTodoListBinding
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.utli.CalendarUtil

/**
 * Created by masegi on 2018/04/13.
 */

class TodoListAdapter(
        var tasks: MutableList<Task>,
        var onTaskClicked: ((Task) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    // MARK: - Initializer

    init { tasks = insertDate(tasks) }


    // MARK: - Property

    private enum class Type(val id: Int) { ITEM(0), HEADER(1) }


    // MARK: - Internal

    internal fun removeItem(position: Int) {

        tasks.removeAt(position)
        notifyItemRemoved(position)
    }

    internal fun insertItem(task: Task, position: Int) {

        tasks.add(position, task)
        notifyItemInserted(position)
    }

    internal fun notifyDataSetChanged(data: MutableList<Task>) {

        tasks = insertDate(data)
        notifyDataSetChanged()
    }


    // MARK: - RecyclerView.Adapter

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        var inflater = LayoutInflater.from(parent?.context)
        return when (viewType) {

            Type.HEADER.id -> HeaderViewHolder(
                    ItemTodoHeaderBinding.inflate(inflater, parent, false)
            )

            else -> {

                val binding = ItemTodoListBinding.inflate(inflater, parent, false)
                ItemViewHolder(binding).apply {

                    binding.root.setOnClickListener { onTaskClicked?.invoke(tasks[adapterPosition]) }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        val task = tasks[position]
        if (task.name.isEmpty()) {

            (holder as HeaderViewHolder).binding.header.text = task.content
        }
        else {

            if (holder is ItemViewHolder) {

                holder.binding.task = task
                holder.binding.time.text = CalendarUtil.calendarToSimpleTime(task.dueAt)
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    override fun getItemViewType(position: Int): Int {

        return if (tasks[position].name.isEmpty()) Type.HEADER.id else Type.ITEM.id
    }


    // MARK: - Private

    private fun insertDate(tasks: MutableList<Task>): MutableList<Task> {

        val tasksWithDate = mutableListOf<Task>()
        var oldDate: String = ""
        var newDate: String = ""
        var index = 0
        tasks.forEach {

            newDate = CalendarUtil.calendarToString(it.dueAt).substring(0..8)
            if (oldDate != newDate) {

                val date = Task(content = CalendarUtil.calendarToTodoDate(it.dueAt))
                tasksWithDate.add(index++, date)
                oldDate = newDate
            }
            tasksWithDate.add(index++, it)
        }
        return tasksWithDate
    }

    class HeaderViewHolder(var binding: ItemTodoHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    class ItemViewHolder(var binding: ItemTodoListBinding) : RecyclerView.ViewHolder(binding.root)
}