package masegi.sho.classtable.presentation.views.editclass

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityEditLessonBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.presentation.NavigationController
import javax.inject.Inject

class EditLessonActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityEditLessonBinding>(
                this,
                R.layout.activity_edit_lesson
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {

            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        if (savedInstanceState == null) {

            navigationController.navigateToEditLesson(
                    DayOfWeek.getWeek(intent.getIntExtra(EXTRA_LESSON_DAY, 0)),
                    intent.getIntExtra(EXTRA_LESSON_START, 1)
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == android.R.id.home) {

            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
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
