package masegi.sho.classtable.kotlin.data.model

/**
 * Created by masegi on 2018/03/04.
 */

enum class DayOfWeek(val rawValue: String) {

    SUN("Sunday"),
    MON("Monday"),
    TUE("Tuesday"),
    WED("Wednesday"),
    THU("Thursday"),
    FRI("Friday"),
    SAT("Saturday");

    companion object {

        internal fun getWeek(ordinal: Int): DayOfWeek = DayOfWeek.values()[ordinal]
    }
}
