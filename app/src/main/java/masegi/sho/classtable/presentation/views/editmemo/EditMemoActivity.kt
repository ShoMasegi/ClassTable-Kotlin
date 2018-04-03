package masegi.sho.classtable.presentation.views.editmemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.data.model.Memo
import org.parceler.Parcels

class EditMemoActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_edit)
    }

    companion object {

        private const val EXTRA_MEMO_BUNDLE = "EXTRA_MEMO_BUNDLE"
        private const val EXTRA_LESSON = "EXTRA_LESSON"


        private fun createIntent(context: Context, memo: Memo): Intent =

                Intent(context, EditMemoActivity::class.java).apply {

                    val bundle = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(memo)) }
                    putExtra(EXTRA_MEMO_BUNDLE, bundle)
                }

        fun start(context: Context, memo: Memo) {

            createIntent(context, memo).let { context.startActivity(it) }
        }
    }
}
