package masegi.sho.classtable.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ViewSettingSubTitleBinding

/**
 * Created by masegi on 2018/04/17.
 */

class SettingSubTitleRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    // MARK: - Property

    internal var onClicked: (() -> Unit)? = null
        set(value) {

            binding.root.setOnClickListener { value?.invoke() }
        }

    internal var title: String?
        set(value) {

            binding.title.text = value
        }
        get() = binding.title.text.toString()

    internal var subtitle: String?
        set(value) {

            binding.subTitle.text = value
        }
        get() = binding.subTitle.text.toString()

    private val binding: ViewSettingSubTitleBinding = ViewSettingSubTitleBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
    )


    // MARK: - View

    constructor(context: Context): this(context, null)

    init {

        run {

            if (isInEditMode) {

                inflate(context, R.layout.view_setting_sub_title, this)
                return@run
            }
            val typedArray: TypedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.SettingSubTitleRow
            )
            title = typedArray.getString(R.styleable.SettingSubTitleRow_settingTitle)
            subtitle = typedArray.getString(R.styleable.SettingSubTitleRow_settingSubTitle)
            typedArray.recycle()
        }
    }

    override fun setEnabled(enabled: Boolean) {

        super.setEnabled(enabled)
        if (enabled) {

            val charBlack = ContextCompat.getColor(context, R.color.char_black)
            binding.title.setTextColor(charBlack)
            binding.subTitle.setTextColor(charBlack)
        }
        else {

            val disableTextColor = ContextCompat.getColor(context, R.color.black_alpha_30)
            binding.title.setTextColor(disableTextColor)
            binding.subTitle.setTextColor(disableTextColor)
        }
    }
}
