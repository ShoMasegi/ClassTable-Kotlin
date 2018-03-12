package masegi.sho.classtable.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by masegi on 2018/03/10.
 */

@Entity(tableName = "memo")
data class Memo(
        @PrimaryKey var lid: Int,
        var tid: Int,
        var content: String
)
