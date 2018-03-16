package masegi.sho.classtable.presentation.adapter

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import masegi.sho.classtable.R
import masegi.sho.classtable.data.LessonDataSource
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.databinding.ItemLessonBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.customview.ClassTable

/**
 * Created by masegi on 2018/03/06.
 */


interface OnTableItemClickListener {

    fun onTableItemClick(view: View, item: Lesson?): Unit
    fun onTableItemLongClick(view: View, item: Lesson?, day: DayOfWeek, start: Int): Unit
}

class ClassTableAdapter(
        var dataSource: LessonDataSource,
        private var listener: OnTableItemClickListener
)
{


    // MARK: - Property

    internal lateinit var classTable: ClassTable
    private val prefs = Prefs


    // MARK: - Internal

    internal fun rebuild(source: LessonDataSource) {

        dataSource = source
        classTable.sectionCount = prefs.dayLessonCount
        classTable.weeks = prefs.weeks
        classTable.build()
    }

    internal fun datasetChanged(source: LessonDataSource) {

        dataSource = source
        classTable.bindContentData()
    }

    internal fun getItemAt(day: DayOfWeek, start: Int): Lesson? = dataSource.getLesson(day, start)

    internal fun createViewHolder(parent: ViewGroup): LessonViewHolder {

        val view = LayoutInflater.from(classTable.context).inflate(R.layout.item_lesson, parent, false)
        return LessonViewHolder(view)
    }

    internal fun bind(view: View, day: DayOfWeek, start: Int) {

        val item = getItemAt(day, start)
        LessonViewHolder(view).apply {

            itemView.setOnClickListener { listener.onTableItemClick(view, item) }
            itemView.setOnLongClickListener {

                listener.onTableItemLongClick(view, item, day, start)
                return@setOnLongClickListener false
            }
            lesson = item
        }
    }


    // MARK: - ViewHolder

    class LessonViewHolder(val itemView: View) {

        var lesson: Lesson? = null
            set(value) {

                binding.lesson = value
                field = value
            }
        private var binding: ItemLessonBinding = DataBindingUtil.bind(itemView)
    }
}
