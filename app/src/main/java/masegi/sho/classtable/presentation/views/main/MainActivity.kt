package masegi.sho.classtable.presentation.views.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.databinding.ActivityMainBinding
import masegi.sho.classtable.presentation.views.main.home.HomeFragment
import masegi.sho.classtable.presentation.views.main.today.TodayFragment
import masegi.sho.classtable.presentation.views.main.todo.TodoFragment

class MainActivity : DaggerAppCompatActivity() {


    // MARK: - Property

    private val binding by lazy {

        DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main
        )
    }
    private var homeFragment: Fragment? = null
    private var todayFragment: Fragment? = null
    private var todoFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        initFragment(savedInstanceState)
        binding.include!!.bottomNavigation.setOnNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.nav_home -> switchFragment(homeFragment!!, HomeFragment.tag)
                R.id.nav_today -> switchFragment(todayFragment!!, TodayFragment.tag)
                R.id.nav_todo -> switchFragment(todoFragment!!, TodayFragment.tag)
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initFragment(savedInstanceState: Bundle?) {

        val manager: FragmentManager = supportFragmentManager
        manager.apply {

            homeFragment = findFragmentByTag(HomeFragment.tag)
            todayFragment = findFragmentByTag(TodayFragment.tag)
            todoFragment = findFragmentByTag(TodoFragment.tag)
        }
        if (homeFragment == null) {

            homeFragment = HomeFragment.newInstance()
        }
        if (todayFragment == null) {

            todayFragment = TodayFragment.newInstance()
        }
        if (todoFragment == null) {

            todoFragment = TodoFragment.newInstance()
        }
        if (savedInstanceState == null) {

            switchFragment(homeFragment!!, HomeFragment.tag)
            binding.include!!.bottomNavigation.selectedItemId = R.id.nav_home
        }
    }

    private fun switchFragment(fragment: Fragment, tag: String): Boolean {

        if (fragment.isAdded) return false

        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        val currentFragment = manager.findFragmentById(R.id.content)
        if (currentFragment != null) {

            if (currentFragment is TodayFragment) {

                binding.tabLayout.visibility = View.GONE
            }
            transaction.detach(currentFragment)
        }
        if (fragment.isDetached) {

            transaction.attach(fragment)
        }
        else {

            transaction.add(R.id.content, fragment, tag)
        }
        if (fragment is TodayFragment) {

            binding.tabLayout.visibility = View.VISIBLE
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        manager.executePendingTransactions()
        return true
    }
}
