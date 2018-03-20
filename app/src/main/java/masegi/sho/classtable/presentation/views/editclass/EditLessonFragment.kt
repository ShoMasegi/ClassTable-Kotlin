package masegi.sho.classtable.presentation.views.editclass


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf

import masegi.sho.classtable.R
import masegi.sho.classtable.kotlin.data.model.DayOfWeek


/**
 * A simple [Fragment] subclass.
 */
class EditLessonFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_edit_lesson, container, false)
    }

    companion object {

        private const val EXTRA_LESSON_DAY = "EXTRA_LESSON_DAY"
        private const val EXTRA_LESSON_START = "EXTRA_LESSON_START"

        internal fun newInstance(day: DayOfWeek, start: Int): EditLessonFragment =
                EditLessonFragment().apply {

                    arguments = bundleOf(
                            EXTRA_LESSON_DAY to day.ordinal,
                            EXTRA_LESSON_START to start
                    )
                }
    }
}// Required empty public constructor
