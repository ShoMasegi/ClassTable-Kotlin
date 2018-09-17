package masegi.sho.classtable.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.parceler.Parcel

/**
 * Created by masegi on 2018/03/10.
 */

@Parcel
@Entity(tableName = "memo")
data class Memo(
        @PrimaryKey var lid: Int = 0,
        var tid: Int = 0,
        var content: String = ""
)
