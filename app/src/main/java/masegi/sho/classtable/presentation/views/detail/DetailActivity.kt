package masegi.sho.classtable.presentation.views.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityDetailBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.NavigationController
import org.parceler.Parcels
import javax.inject.Inject


class DetailActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityDetailBinding>(
                this,
                R.layout.activity_detail
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        val lesson: Lesson = Parcels.unwrap(intent.getBundleExtra(EXTRA_LESSON_BUNDLE).getParcelable(EXTRA_LESSON))
        this.setTheme(lesson.theme.themeResId)

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = lesson.name
        if (savedInstanceState == null) {

            navigationController.navigateToDetail(lesson)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return if (item?.itemId == android.R.id.home) {

            finish()
            true
        }
        else super.onOptionsItemSelected(item)
    }

    companion object {

        private const val EXTRA_LESSON_BUNDLE = "EXTRA_LESSON_BUNDLE"
        private const val EXTRA_LESSON = "EXTRA_LESSON"


        private fun createIntent(context: Context, lesson: Lesson?): Intent =

            Intent(context, DetailActivity::class.java).apply {

                val bundle = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                putExtra(EXTRA_LESSON_BUNDLE, bundle)
            }

        fun start(context: Context, lesson: Lesson) {

            createIntent(context, lesson).let { context.startActivity(it) }
        }
    }
}
