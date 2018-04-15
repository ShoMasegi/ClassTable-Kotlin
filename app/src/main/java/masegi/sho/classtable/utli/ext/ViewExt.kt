package masegi.sho.classtable.utli.ext

import android.os.Build
import android.view.View

/**
 * Created by masegi on 2018/04/15.
 */

var View.elevationForPostLollipop: Float
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) elevation else 0F
    set(value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { elevation = value }
    }

