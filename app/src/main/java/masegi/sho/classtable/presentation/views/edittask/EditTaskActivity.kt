package masegi.sho.classtable.presentation.views.edittask

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityEditTaskBinding
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.kotlin.data.model.ThemeColor
import masegi.sho.classtable.presentation.NavigationController
import org.parceler.Parcels
import javax.inject.Inject

class EditTaskActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityEditTaskBinding>(
                this,
                R.layout.activity_edit_task
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        val bundle: Bundle = intent.getBundleExtra(EXTRA_TASK_BUNDLE)
        val theme: ThemeColor = Parcels.unwrap(bundle.getParcelable(EXTRA_THEME))
        this.setTheme(theme.themeResId)

        super.onCreate(savedInstanceState)
        binding
        title = this.resources.getString(R.string.todo)
        if (savedInstanceState == null) {

            navigationController.navigateToEditTask(Parcels.unwrap(bundle.getParcelable(EXTRA_TASK)))
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

        private const val EXTRA_TASK_BUNDLE = "EXTRA_TASK_BUNDLE"
        private const val EXTRA_TASK = "EXTRA_TASK"
        private const val EXTRA_THEME = "EXTRA_THEME"

        private fun createIntent(context: Context, task: Task, theme: ThemeColor): Intent =

                Intent(context, EditTaskActivity::class.java).apply {

                    val bundle = Bundle().apply {

                        putParcelable(EXTRA_TASK, Parcels.wrap(task))
                        putParcelable(EXTRA_THEME, Parcels.wrap(theme))
                    }
                    putExtra(EXTRA_TASK_BUNDLE, bundle)
                }

        fun start(context: Context, task: Task, theme: ThemeColor) {

            createIntent(context, task, theme).let { context.startActivity(it) }
        }
    }
}
