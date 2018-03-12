package masegi.sho.classtable.presentation.views.main.today


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import masegi.sho.classtable.R


/**
 * A simple [Fragment] subclass.
 */
class LessonListFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson_list, container, false)
    }

}// Required empty public constructor
