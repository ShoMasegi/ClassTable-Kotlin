package masegi.sho.classtable.kotlin.data.model

import masegi.sho.classtable.R

/**
 * Created by masegi on 2018/03/04.
 */

enum class ThemeColor(
        val id: Int,
        val themeResId: Int,
        val primaryColorResId: Int,
        val primaryColorDarkResId: Int,
        val colorName: String
)
{

    DEFAULT(0, R.style.AppTheme, R.color.default_color, R.color.grey600, "Default color"),
    RED(1, R.style.AppTheme_Red, R.color.colorPrimary_red, R.color.colorPrimaryDark_red, "Red color"),
    PURPLE(2, R.style.AppTheme_Purple, R.color.colorPrimary_purple, R.color.colorPrimaryDark_purple, "Purple color"),
    BLUE(3, R.style.AppTheme_Blue, R.color.colorPrimary_blue, R.color.colorPrimaryDark_blue, "Blue color"),
    LIGHTBLUE(4, R.style.AppTheme_LightBlue, R.color.colorPrimary_light_blue, R.color.colorPrimaryDark_light_blue, "Light blue color"),
    TEAL(5, R.style.AppTheme_Teal, R.color.colorPrimary_teal, R.color.colorPrimaryDark_teal, "Teal color"),
    GREEN(6, R.style.AppTheme_Green, R.color.colorPrimary_green, R.color.colorPrimaryDark_green, "Green color"),
    YELLOW(7, R.style.AppTheme_Yellow, R.color.colorPrimary_yellow, R.color.colorPrimaryDark_yellow, "Yellow color"),
    ORANGE(8, R.style.AppTheme_Orange, R.color.colorPrimary_orange, R.color.colorPrimaryDark_orange, "Orange color"),
    BROWN(9, R.style.AppTheme_Brown, R.color.colorPrimary_brown, R.color.colorPrimaryDark_brown, "Brown color");


    companion object {

        val primaryColorResIdList: ArrayList<Int>
            get() {

                val colors = ArrayList<Int>()
                ThemeColor.values().forEach {

                    colors.add(it.primaryColorResId)
                }
                return colors
            }

        fun get(id: Int): ThemeColor {

            return ThemeColor.values().firstOrNull { it.id == id } ?: DEFAULT
        }

        fun getByPrimaryColorResId(colorResId: Int): ThemeColor {

            return ThemeColor.values().firstOrNull { it.primaryColorResId == colorResId } ?: DEFAULT
        }
    }
}
