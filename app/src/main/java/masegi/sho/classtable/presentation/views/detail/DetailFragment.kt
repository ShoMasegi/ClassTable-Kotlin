package masegi.sho.classtable.presentation.views.detail


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

import masegi.sho.classtable.databinding.FragmentDetailBinding
import masegi.sho.classtable.kotlin.data.model.Lesson
import org.parceler.Parcels
import javax.inject.Inject


class DetailFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentDetailBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

        private const val EXTRA_LESSON = "EXTRA_LESSON"

        internal fun newInstance(lesson: Lesson): DetailFragment =
                DetailFragment().apply {

                    arguments = Bundle().apply { putParcelable(EXTRA_LESSON, Parcels.wrap(lesson)) }
                }
    }

}// Required empty public constructor
