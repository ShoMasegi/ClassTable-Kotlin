package masegi.sho.classtable.kotlin.data.model

import org.parceler.Parcel

/**
 * Created by masegi on 2018/03/04.
 */

@Parcel
data class Attendance(
        var attend: Int = 0,
        var late: Int = 0,
        var absence: Int = 0
)