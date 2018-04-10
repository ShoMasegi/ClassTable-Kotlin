package masegi.sho.classtable.presentation.views.editmemo


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

import masegi.sho.classtable.data.model.Memo
import masegi.sho.classtable.databinding.FragmentEditMemoBinding
import org.parceler.Parcels
import javax.inject.Inject


class EditMemoFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentEditMemoBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditMemoViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(EditMemoViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentEditMemoBinding.inflate(inflater, container, false)
        val memo: Memo = Parcels.unwrap(arguments!!.getParcelable(EXTRA_MEMO))
        binding.memo = memo
        binding.title.text = arguments!!.getString(EXTRA_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }


    // MARK: - Private

    private fun setupViews() {

        binding.saveButton.setOnClickListener {

            binding.memo?.let {

                if (!(it.content.isNullOrEmpty())) { viewModel.save(it) }
            }
            activity?.finish()
        }
        binding.closeButton.setOnClickListener { activity?.finish() }
    }


    companion object {

        private const val EXTRA_MEMO = "EXTRA_MEMO"
        private const val EXTRA_TITLE = "EXTRA_TITLE"

        internal fun newInstance(memo: Memo, title: String): EditMemoFragment =
                EditMemoFragment().apply {

                    arguments = Bundle().apply {

                        putParcelable(EXTRA_MEMO, Parcels.wrap(memo))
                        putString(EXTRA_TITLE, title)
                    }
                }
    }
}// Required empty public constructor
