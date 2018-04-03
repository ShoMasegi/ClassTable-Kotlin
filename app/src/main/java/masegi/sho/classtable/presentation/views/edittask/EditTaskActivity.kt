package masegi.sho.classtable.presentation.views.edittask

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.kotlin.data.model.Task
import org.parceler.Parcels

class EditTaskActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)
    }


    companion object {

        private const val EXTRA_TASK_BUNDLE = "EXTRA_TASK_BUNDLE"
        private const val EXTRA_TASK = "EXTRA_TASK"

        private fun createIntent(context: Context, task: Task): Intent =

                Intent(context, EditTaskActivity::class.java).apply {

                    if (task != null) {

                        val bundle = Bundle().apply { putParcelable(EXTRA_TASK, Parcels.wrap(task)) }
                        putExtra(EXTRA_TASK_BUNDLE, bundle)
                    }
                }

        fun start(context: Context, task: Task) {

            createIntent(context, task).let { context.startActivity(it) }
        }
    }
}
