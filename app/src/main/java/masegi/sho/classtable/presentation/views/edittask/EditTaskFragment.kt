package masegi.sho.classtable.presentation.views.edittask


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import masegi.sho.classtable.databinding.FragmentEditTaskBinding
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.NavigationController
import org.parceler.Parcels
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class EditTaskFragment : Fragment() {


    // MARK: - Property

    private lateinit var binding: FragmentEditTaskBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditTaskViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(EditTaskViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        binding.task = Parcels.unwrap(arguments!!.getParcelable(EXTRA_TASK))
        return binding.root
    }


    // MARK: - Private


    companion object {

        private const val EXTRA_TASK = "EXTRA_TASK"
        private const val EXTRA_TITLE = "EXTRA_TITLE"

        internal fun newInstance(task: Task, title: String): EditTaskFragment =
                EditTaskFragment().apply {

                    arguments = Bundle().apply {

                        putParcelable(EXTRA_TASK, Parcels.wrap(task))
                        putString(EXTRA_TITLE, title)
                    }
                }
    }
}// Required empty public constructor
