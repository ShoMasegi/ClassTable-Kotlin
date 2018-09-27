package masegi.sho.classtable.presentation.views.setting

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivitySettingBinding
import masegi.sho.classtable.presentation.NavigationController
import javax.inject.Inject

class SettingActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivitySettingBinding>(
                this,
                R.layout.activity_setting
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.subtitle = null
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.settings)
        if (savedInstanceState == null) {

            navigationController.navigateToSetting()
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

                Intent(context, SettingActivity::class.java)

        fun start(context: Context) {

            createIntent(context).let { context.startActivity(it) }
        }
    }
}
