package masegi.sho.classtable.presentation.views.edittask


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.R

import masegi.sho.classtable.databinding.FragmentEditTaskBinding
import masegi.sho.classtable.kotlin.data.model.Task
import masegi.sho.classtable.presentation.NavigationController
import org.parceler.Parcels
import javax.inject.Inject


class EditTaskFragment : DaggerFragment() {


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }


    // MARK: - Private

    private fun setupViews() {

        binding.saveButton.setOnClickListener {

            if (binding.task?.name.isNullOrEmpty()) {

                showSnackBar(R.string.null_todo_taskName)
                return@setOnClickListener
            }
            binding.task?.let { viewModel.save(it) }
            activity?.finish()
        }
        binding.closeButton.setOnClickListener { activity?.finish() }
    }

    private fun showSnackBar(@StringRes message: Int) =
            Snackbar.make(binding.parentLayout, message, Snackbar.LENGTH_LONG)
                    .show()

    companion object {

        private const val EXTRA_TASK = "EXTRA_TASK"

        internal fun newInstance(task: Task): EditTaskFragment =
                EditTaskFragment().apply {

                    arguments = Bundle().apply {

                        putParcelable(EXTRA_TASK, Parcels.wrap(task))
                    }
                }
    }
}// Required empty public constructor
