package masegi.sho.classtable.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewSettingEditBinding

/**
 * Created by masegi on 2018/04/06.
 */

class SettingEditRowView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    // MARK: - Property

    private val binding: ViewSettingEditBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_setting_edit,
            this,
            true
    )

    internal var text: String?
        get() = binding.title.text.toString()
        set(value) {

            binding.title.setText(value, TextView.BufferType.NORMAL)
        }

    internal var iconImage: Drawable? = null
        set(value) {

            binding.titleIcon.setImageDrawable(value)
        }

    internal var hint: String?
        get() = binding.title.hint.toString()
        set(value) {

            binding.title.hint = value
        }

    // MARK: - View

    constructor(context: Context): this(context, null)

    init {

        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingEditRowView)
        iconImage = a.getDrawable(R.styleable.SettingEditRowView_titleIcon)
        text = a.getString(R.styleable.SettingEditRowView_text)
        hint = a.getString(R.styleable.SettingEditRowView_hint)
        a.recycle()
    }
}