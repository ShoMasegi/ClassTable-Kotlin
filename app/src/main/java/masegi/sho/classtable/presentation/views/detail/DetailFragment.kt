package masegi.sho.classtable.presentation.views.detail


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment

import masegi.sho.classtable.databinding.FragmentDetailBinding
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

}// Required empty public constructor
