package masegi.sho.classtable.presentation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import masegi.sho.classtable.R
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.views.detail.DetailActivity
import masegi.sho.classtable.presentation.views.detail.DetailFragment
import masegi.sho.classtable.presentation.views.editlesson.EditLessonActivity
import masegi.sho.classtable.presentation.views.editlesson.EditLessonFragment
import masegi.sho.classtable.presentation.views.edittask.EditTaskActivity
import masegi.sho.classtable.presentation.views.main.home.HomeFragment
import masegi.sho.classtable.presentation.views.main.today.TodayFragment
import masegi.sho.classtable.presentation.views.main.todo.TodoFragment
import javax.inject.Inject

/**
 * Created by masegi on 2018/03/07.
 */

class NavigationController @Inject constructor(private val activity: AppCompatActivity) {


    // MARK: - Property

    private val containerId: Int = R.id.content
    private val fragmentManager: FragmentManager = activity.supportFragmentManager


    // MARK: - Internal

    internal fun navigateToHome() {

        replaceFragment(HomeFragment.newInstance())
    }

    internal fun navigateToToday() {

        replaceFragment(TodayFragment.newInstance())
    }

    internal fun navigateToTodo() {

        replaceFragment(TodoFragment.newInstance())
    }

    internal fun navigateToEditLessonActivity(lesson: Lesson) {

        EditLessonActivity.start(activity, lesson)
    }

    internal fun navigateToEditLesson(lesson: Lesson) {

        replaceFragment(EditLessonFragment.newInstance(lesson))
    }

    internal fun navigateToDetailActivity(lesson: Lesson) {

        DetailActivity.start(activity, lesson)
    }

    internal fun navigateToDetail(lesson: Lesson) {

        replaceFragment(DetailFragment.newInstance(lesson))
    }

    internal fun navigateToEditTaskActivity(task: Task) {

        EditTaskActivity.start(activity, task)
    }

    internal fun navigateToEditTask(task: Task) {

    }


    // MARK: - Private

    private fun replaceFragment(fragment: Fragment) {

        val transaction = fragmentManager.beginTransaction()
                .replace(containerId, fragment, null)
        if (fragmentManager.isStateSaved) {

            transaction.commitAllowingStateLoss()
        }
        else {

            transaction.commit()
        }
    }
}