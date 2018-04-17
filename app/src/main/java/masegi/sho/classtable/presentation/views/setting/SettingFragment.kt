package masegi.sho.classtable.presentation.views.setting


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.data.model.Pref

import masegi.sho.classtable.databinding.FragmentSettingBinding
import javax.inject.Inject


class SettingFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentSettingBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SettingViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(SettingViewModel::class.java)
    }


    // MARK: - Fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSettingBinding.inflate(inflater, container!!, false)
        binding.pref = Pref(name = "Sample", dayLessonCount = 5)
        return binding.root
    }

    companion object {

        internal fun newInstance(): SettingFragment = SettingFragment()
    }
}// Required empty public constructor
