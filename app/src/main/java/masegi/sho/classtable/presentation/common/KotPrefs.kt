package masegi.sho.classtable.presentation.common

import com.chibatching.kotpref.KotprefModel
import masegi.sho.classtable.R

/**
 * Created by masegi on 2018/03/06.
 */

object KotPrefs : KotprefModel() {

    override val kotprefName: String = "classtable_prefs"

    var dayLessonCount: Int by intPref(
            default = 0,
            key = R.string.pref_key_day_lesson_count
    )
}
