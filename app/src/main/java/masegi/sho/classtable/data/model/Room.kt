package masegi.sho.classtable.kotlin.data.model

import android.arch.persistence.room.ColumnInfo

/**
 * Created by masegi on 2018/03/04.
 */

data class Room(
        @ColumnInfo(name= "room_name") var name: String
)