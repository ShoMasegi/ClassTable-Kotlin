package masegi.sho.classtable.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
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
