package masegi.sho.classtable.presentation.views.editlesson

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityEditLessonBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.NavigationController
import org.parceler.Parcels
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
        binding
        if (savedInstanceState == null) {

            navigationController.navigateToEditLesson(
                    Parcels.unwrap(intent.getBundleExtra(EXTRA_LESSON_BUNDLE).getParcelable(EXTRA_LESSON))
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

        private const val EXTRA_LESSON_BUNDLE = "EXTRA_LESSON_BUNDLE"
        private const val EXTRA_LESSON = "EXTRA_LESSON"


        private fun createIntent(context: Context, lesson: Lesson): Intent =

                Intent(context, EditLessonActivity::class.java).apply {

                    val bundle = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                    putExtra(EXTRA_LESSON_BUNDLE, bundle)
                }

        fun start(context: Context, lesson: Lesson) {

            createIntent(context, lesson).let { context.startActivity(it) }
        }
    }
}
