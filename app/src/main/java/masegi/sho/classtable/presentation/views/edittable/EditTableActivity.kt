package masegi.sho.classtable.presentation.views.edittable

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityEditTableBinding
import masegi.sho.classtable.presentation.NavigationController
import javax.inject.Inject

class EditTableActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityEditTableBinding>(
                this,
                R.layout.activity_edit_table
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.settings_edit_table)
        if (savedInstanceState == null) {

            navigationController.navigateToEditTable()
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

        private fun createIntent(context: Context): Intent =
            Intent(context, EditTableActivity::class.java)

        fun start(context: Context) {

            createIntent(context).let { context.startActivity(it) }
        }
    }
}
