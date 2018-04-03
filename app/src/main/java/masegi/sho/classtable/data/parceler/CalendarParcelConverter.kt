package masegi.sho.classtable.data.parceler

import android.os.Parcel
import masegi.sho.classtable.utli.CalendarUtil
import org.parceler.ParcelConverter
import java.util.*

/**
 * Created by masegi on 2018/04/03.
 */

class CalendarParcelConverter : ParcelConverter<Calendar> {

    override fun fromParcel(parcel: Parcel?): Calendar {

        return if (parcel != null) {

            val dateString = parcel.readString()
            CalendarUtil.stringToCalendar(dateString)
        }
        else {

            Calendar.getInstance()
        }
    }

    override fun toParcel(input: Calendar?, parcel: Parcel?) {

        input?.let {

            parcel?.writeString(CalendarUtil.calendarToString(it))
        }
    }
}
