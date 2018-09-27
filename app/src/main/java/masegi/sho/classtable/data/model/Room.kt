package masegi.sho.classtable.kotlin.data.model

import androidx.room.ColumnInfo
import org.parceler.Parcel

/**
 * Created by masegi on 2018/03/04.
 */

@Parcel
data class Room(
        @ColumnInfo(name= "room_name") var name: String = ""
)