package masegi.sho.classtable.presentation.views.main.today

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumNullableValuePref
import masegi.sho.classtable.R
import masegi.sho.classtable.kotlin.data.model.DayOfWeek

/**
 * Created by masegi on 2018/04/20.
 */

object PreviousDayPrefs : KotprefModel() {

    override val kotprefName: String = "previous_day_prefs"

    var previousDayTab: DayOfWeek? by enumNullableValuePref(key = R.string.pref_key_previous_day)
}