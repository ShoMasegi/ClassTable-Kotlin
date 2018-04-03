package masegi.sho.classtable.data.parceler

import android.os.Parcel
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import org.parceler.ParcelConverter

/**
 * Created by masegi on 2018/03/30.
 */

class ThemeParcelConverter : ParcelConverter<ThemeColor> {

    override fun fromParcel(parcel: Parcel?): ThemeColor {

        return if (parcel != null) {

            val id = parcel.readInt()
            if (id < 0) ThemeColor.DEFAULT else ThemeColor.get(id)
        }
        else {

            ThemeColor.DEFAULT
        }
    }

    override fun toParcel(input: ThemeColor?, parcel: Parcel?) {

        parcel?.writeInt(input?.id ?: 0)
    }

}
