package masegi.sho.classtable.presentation.views.detail

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import masegi.sho.classtable.R


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {

        private const val EXTRA_LESSON_ID = "EXTRA_LESSON_ID"

        private fun createIntent(context: Context, lid: Int?): Intent =

            Intent(context, DetailActivity::class.java).apply {

                putExtra(EXTRA_LESSON_ID, lid)
            }

        fun start(context: Context, lid: Int) {

            createIntent(context, lid).let { context.startActivity(it) }
        }
    }
}
