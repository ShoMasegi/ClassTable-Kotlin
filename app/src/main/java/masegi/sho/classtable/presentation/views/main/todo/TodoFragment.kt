package masegi.sho.classtable.presentation.views.main.todo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import masegi.sho.classtable.R


class TodoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    companion object {

        val tag: String = TodoFragment.toString()
        fun newInstance(): TodoFragment = TodoFragment()
    }
}// Required empty public constructor
