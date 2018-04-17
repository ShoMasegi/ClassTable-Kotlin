package masegi.sho.classtable.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewSettingSwitchRowBinding

/**
 * Created by masegi on 2018/04/17.
 */

class SettingSwitchRowView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    // MARK: - Property

    internal var onCheckedChanged: ((Boolean) -> Unit)? = null
        set(value) {

            binding.switchView.setOnCheckedChangeListener { _, isChecked -> value?.invoke(isChecked) }
        }

    internal var title: String?
        set(value) {

            binding.switchTitle.text = value
        }
        get() = binding.switchTitle.text.toString()

    internal var description: String?
        set(value) {

            binding.switchDescription.text = value
        }
        get() = binding.switchDescription.text.toString()

    private val binding: ViewSettingSwitchRowBinding =
            ViewSettingSwitchRowBinding.inflate(
                    LayoutInflater.from(context),
                    this,
                    true
            )


    // MARK: - Internal

    internal fun setDefault(defaultValue: Boolean) {

        binding.switchView.isChecked = defaultValue
    }


    // MARK: - View

    constructor(context: Context): this(context, null)

    init {

        run {
            if (isInEditMode) {

                inflate(context, R.layout.view_setting_switch_row, this)
                return@run
            }
            val typedArray: TypedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.SettingSwitchRowView
            )
            title = typedArray.getString(R.styleable.SettingSwitchRowView_settingTitle)
            description = typedArray.getString(R.styleable.SettingSwitchRowView_settingDescription)
            binding.root.setOnClickListener { toggle() }
            typedArray.recycle()
        }
    }

    override fun setEnabled(enabled: Boolean) {

        super.setEnabled(enabled)
        binding.root.isEnabled = enabled
        binding.switchView.isEnabled = enabled
        if (enabled) {

            binding.switchTitle.setTextColor(ContextCompat.getColor(context, R.color.black))
            binding.switchDescription.setTextColor(ContextCompat.getColor(context, R.color.sub_char_black))
        }
        else {

            val disabledTextColor = ContextCompat.getColor(context, R.color.black_alpha_30)
            binding.switchTitle.setTextColor(disabledTextColor)
            binding.switchDescription.setTextColor(disabledTextColor)
        }
    }


    // MARK: - Private

    private fun toggle() {

        val isChecked: Boolean = binding.switchView.isChecked
        binding.switchView.isChecked = !isChecked
    }
}
