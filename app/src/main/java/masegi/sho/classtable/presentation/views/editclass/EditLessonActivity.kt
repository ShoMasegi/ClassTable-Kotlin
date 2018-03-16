package masegi.sho.classtable.presentation.views.editclass

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import masegi.sho.classtable.R
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson

class EditLessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lesson)
    }

    companion object {

        private const val EXTRA_LESSON_DAY = "EXTRA_LESSON_DAY"
        private const val EXTRA_LESSON_START = "EXTRA_LESSON_START"

        private fun createIntent(context: Context, day: DayOfWeek, start: Int): Intent =

                Intent(context, EditLessonActivity::class.java).apply {

                    putExtra(EXTRA_LESSON_DAY, day.ordinal)
                    putExtra(EXTRA_LESSON_START, start)
                }

        fun start(context: Context, day: DayOfWeek, start: Int) {

            createIntent(context, day, start).let { context.startActivity(it) }
        }
    }
}
