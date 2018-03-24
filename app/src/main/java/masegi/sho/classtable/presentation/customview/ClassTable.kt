package masegi.sho.classtable.presentation.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import masegi.sho.classtable.R
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.presentation.adapter.ClassTableAdapter

/**
 * Created by masegi on 2018/03/06.
 */

class ClassTable(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {


    // MARK: - Property

    var weeks: List<DayOfWeek> = listOf()
        set(value) {

            field = value
            setMetrics(value.size)
        }

    var sectionCount: Int = 5

    var adapter: ClassTableAdapter? = null
        set(value) {

            value?.classTable = this
            field = value
        }

    private val scale: Float = resources.displayMetrics.density
    private val parentWidth = resources.displayMetrics.widthPixels - (scale*18)
    private var width: Float = 0F
    private var width0: Float = 0F
    private var width1: Float = 0F
    private var height0: Float = 0F
    private var height1: Float = 0F
    private var margin: Int = 0


    // MARK: - Constructor

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)


    // MARK: - Internal

    internal fun build() {

        removeAllViews()
        rowCount = Prefs.dayLessonCount + 1
        columnCount = Prefs.weeks.size + 1
        setupFrame()
        setupContent()
    }

    internal fun bindContentData() {

        if (adapter == null) return
        for (colNum in 1 until weeks.size + 1) {

            val day = weeks[colNum - 1]
            for (rowNum in 1 until sectionCount + 1) {

                val offset = weeks.size + sectionCount
                val position = offset + (colNum - 1) * sectionCount + rowNum
                val childView = getChildAt(position)
                adapter!!.bind(childView, day, rowNum)
            }
        }
    }


    // MARK: - Private

    private fun setMetrics(count: Int) {

        val weight: Int = if (count > 5) 5 else count
        margin = if((scale/2).toInt() > 0) (scale/2).toInt() else 1
        width = parentWidth - margin * (count+1)
        width0 = width / 20
        width1 = (width - width0) / weight
        height0 = width1 / 4
        height1 = if (count > 4) width1 * 5 / 4 else width1
    }

    private fun setupFrame() {

        var rowSpec: GridLayout.Spec
        var colSpec: GridLayout.Spec
        var childParams: GridLayout.LayoutParams


        for (colNum in 0 until weeks.size + 1) {

            for (rowNum in 0 until sectionCount + 1) {

                if (!(colNum == 0 || rowNum == 0)) break

                var childView: TextView = TextView(context)
                childView.apply {
                    gravity = Gravity.CENTER
                    setBackgroundResource(R.drawable.simple_border)
                }
                colSpec = GridLayout.spec(colNum)
                rowSpec = GridLayout.spec(rowNum)
                childParams = GridLayout.LayoutParams(rowSpec, colSpec)
                val textSize = if (sectionCount > 4) 11F else 12F

                if (colNum == 0) {

                    if (rowNum == 0) {

                        childView.textSize = textSize
                        childParams.width = width0.toInt()
                        childParams.height = height0.toInt()
                    }
                    else {

                        childView.text = rowNum.toString()
                        childView.textSize = 12F
                        childParams.width = width0.toInt()
                        childParams.height = height1.toInt()
                    }
                    childParams.setMargins(margin, margin, margin, margin)
                    addView(childView, childParams)
                }
                else {

                    if (rowNum == 0) {

                        childView.textSize = textSize
                        childView.text = weeks[colNum - 1].toString()
                        childParams.width = width1.toInt()
                        childParams.height = height0.toInt()
                        childParams.setMargins(margin, margin, margin, margin)
                        addView(childView, childParams)
                    }
                }
            }
        }
    }

    internal fun setupContent() {

        if (adapter == null) return

        var rowSpec: GridLayout.Spec
        var colSpec: GridLayout.Spec
        var childParams: GridLayout.LayoutParams
        var holder: ClassTableAdapter.LessonViewHolder

        for (colNum in 1 until weeks.size + 1) {

            colSpec = GridLayout.spec(colNum)
            val day = weeks[colNum - 1]
            var rowNum = 1
            while (rowNum < sectionCount + 1) {

                val span = adapter?.getItemAt(day, rowNum)?.section ?: 1
                rowSpec = GridLayout.spec(rowNum, span)
                childParams = GridLayout.LayoutParams(rowSpec, colSpec)
                childParams.width = width1.toInt()
                childParams.height = height1.toInt() * span + 2 * margin * (span - 1)
                childParams.setMargins(margin, margin, margin, margin)
                holder = adapter!!.createViewHolder(this)
                adapter!!.bind(holder.itemView, day, rowNum)
                addView(holder.itemView, childParams)
                rowNum += span
            }
        }
    }
}