package masegi.sho.classtable.presentation.views.main

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.MenuRes
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityMainBinding
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.utli.ext.elevationForPostLollipop
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    @Inject lateinit var navigationController: NavigationController

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
        )
    }


    // MARK: - Activity

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setupBottomNavigation(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        super.onRestoreInstanceState(savedInstanceState)
        setupToolbar(BottomNavigationItem.forId(binding.bottomNavigation.selectedItemId))
    }


    // MARK: - Private

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            val navigationItem = BottomNavigationItem.forId(item.itemId)
            setupToolbar(navigationItem)
            navigationItem.navigate(navigationController)
            true
        }
        if (savedInstanceState == null) {

            binding.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    private fun setupToolbar(navigationItem: BottomNavigationItem) {

        binding.toolbar.elevationForPostLollipop = if (navigationItem.isUseToolbarElevation) {

            resources.getDimension(R.dimen.elevation_app_bar)
        }
        else {

            0F
        }
    }


    // MARK: - BottomNavigationItem

    enum class BottomNavigationItem(
            @MenuRes val menuId: Int,
            val isUseToolbarElevation: Boolean,
            val navigate: NavigationController.() -> Unit
    )
    {

        HOME(R.id.nav_home, true, { navigateToHome() }),
        TODAY(R.id.nav_today, false, { navigateToToday() }),
        TODO(R.id.nav_todo, true, { navigateToTodo() });

        companion object {

            fun forId(@IdRes id: Int): BottomNavigationItem {

                return values().first { it.menuId == id }
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)

        fun start(context: Context) {
            createIntent(context).let {

                it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(it)
            }
        }
    }
}
