package masegi.sho.classtable.presentation.views.editmemo

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.databinding.ActivityMemoEditBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.NavigationController
import org.parceler.Parcels
import javax.inject.Inject

class EditMemoActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityMemoEditBinding>(
                this,
                R.layout.activity_memo_edit
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle: Bundle = intent.getBundleExtra(EXTRA_MEMO_BUNDLE)
        val lesson: Lesson = Parcels.unwrap(bundle.getParcelable(EXTRA_LESSON))
        this.setTheme(lesson.theme.themeResId)

        super.onCreate(savedInstanceState)
        binding
        title = resources.getString(R.string.memo)
        if (savedInstanceState == null) {

            navigationController.navigateToEditMemo(
                    Parcels.unwrap(bundle.getParcelable(EXTRA_MEMO)),
                    lesson.name
            )
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

        private const val EXTRA_MEMO_BUNDLE = "EXTRA_MEMO_BUNDLE"
        private const val EXTRA_LESSON = "EXTRA_LESSON"
        private const val EXTRA_MEMO = "EXTRA_MEMO"

        private fun createIntent(context: Context, memo: Memo?, lesson: Lesson): Intent =

                Intent(context, EditMemoActivity::class.java).apply {

                    val bundle = Bundle().apply {

                        val memo = memo ?: Memo(lesson.id, lesson.tid, "")
                        putParcelable(EXTRA_LESSON, Parcels.wrap(lesson))
                        putParcelable(EXTRA_MEMO, Parcels.wrap(memo))
                    }
                    putExtra(EXTRA_MEMO_BUNDLE, bundle)
                }

        fun start(context: Context, memo: Memo?, lesson: Lesson) {

            createIntent(context, memo, lesson).let { context.startActivity(it) }
        }
    }
}
