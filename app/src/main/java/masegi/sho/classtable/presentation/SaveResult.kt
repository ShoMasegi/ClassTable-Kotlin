package masegi.sho.classtable.presentation

import androidx.annotation.StringRes
import masegi.sho.classtable.kotlin.data.model.Lesson

/**
 * Created by masegi on 2018/04/01.
 */

sealed class SaveResult(val inProgress: Boolean) {

    class InProgress : SaveResult(true) {

        override fun equals(other: Any?): Boolean {

            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            return true
        }

        override fun hashCode(): Int = javaClass.hashCode()
    }

    data class Success(var data: Lesson) : SaveResult(false)

    data class Failure(@StringRes val errorMessage: Int?) : SaveResult(false)

    companion object {

        fun inProgress(): SaveResult = InProgress()

        fun success(data: Lesson): SaveResult = Success(data)

        fun failure(@StringRes errorMessage: Int?): SaveResult = Failure(errorMessage)
    }
}
