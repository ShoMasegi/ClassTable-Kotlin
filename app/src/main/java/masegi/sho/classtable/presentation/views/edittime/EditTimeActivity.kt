package masegi.sho.classtable.presentation.views.edittime

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityEditTimeBinding
import masegi.sho.classtable.presentation.NavigationController
import javax.inject.Inject

class EditTimeActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController
    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityEditTimeBinding>(
                this,
                R.layout.activity_edit_time
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        title = resources.getString(R.string.settings_class_time)
        supportActionBar?.let {

            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(true)
        }
        if (savedInstanceState == null) {

            navigationController.navigateToEditTime()
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

                Intent(context, EditTimeActivity::class.java)


        fun start(context: Context) = createIntent(context).let { context.startActivity(it) }
    }
}
