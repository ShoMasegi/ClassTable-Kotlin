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


    // MARK: - Private

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {

        binding.include!!.bottomNavigation.setOnNavigationItemSelectedListener { item ->

            val navigationItem = BottomNavigationItem.forId(item.itemId)
            navigationItem.navigate(navigationController)
            setupToolbar(navigationItem)
            true
        }
        if (savedInstanceState == null) {

            binding.include!!.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    private fun setupToolbar(navigationItem: BottomNavigationItem) {

        if (navigationItem == BottomNavigationItem.TODAY) {

            binding.toolbar.elevation = 0F
            binding.tabLayout.visibility = View.VISIBLE
        }
        else {

            binding.toolbar.elevation = resources.getDimensionPixelSize(R.dimen.elevation_toolbar).toFloat()
            binding.tabLayout.visibility = View.GONE
        }
    }


    // MARK: - BottomNavigationItem

    enum class BottomNavigationItem(
            @MenuRes val menuId: Int,
            val navigate: NavigationController.() -> Unit
    )
    {

        HOME(R.id.nav_home, { navigateToHome() }),
        TODAY(R.id.nav_today, { navigateToToday() }),
        TODO(R.id.nav_todo, { navigateToTodo() });

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
