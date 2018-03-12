package masegi.sho.classtable.util.ext

/**
 * Created by masegi on 2018/03/04.
 */

val Int.ordinal: String
    get() {

        val sufixes = listOf("th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th")
        return when(this % 100) {

            11, 12, 13 -> this.toString() + "th"
            else -> this.toString() + sufixes[this % 10]
        }
    }
