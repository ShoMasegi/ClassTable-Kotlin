package masegi.sho.classtable.presentation.views.main.today


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.os.bundleOf
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.data.model.Memo

import masegi.sho.classtable.databinding.FragmentLessonListBinding
import masegi.sho.classtable.databinding.ItemLessonCardBinding
import masegi.sho.classtable.kotlin.data.model.DayOfWeek
import masegi.sho.classtable.kotlin.data.model.Lesson
import masegi.sho.classtable.presentation.NavigationController
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject


class LessonListFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentLessonListBinding
    @Inject lateinit var navigationController: NavigationController
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LessonListViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(LessonListViewModel::class.java)
    }
    private lateinit var day: DayOfWeek
    private lateinit var listAdapter: LessonListAdapter
    private val isEmpty: ObservableField<Boolean> = ObservableField(false)


    // MARK: - Fragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        day = DayOfWeek.Companion.getValue(arguments!!.getInt(ARG_DAY_OF_WEEK))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentLessonListBinding.inflate(inflater, container, false)
        binding.isEmpty = isEmpty
        listAdapter = LessonListAdapter(listOf()) { navigationController.navigateToDetailActivity(it) }
        binding.listView.run {

            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        viewModel.day = day
        viewModel.data.observe(this) { result ->

            when (result) {

                is Result.Success -> {

                    val data = result.data
                    isEmpty.set(data.isEmpty())
                    listAdapter.data = data
                    listAdapter.notifyDataSetChanged()
                }
                is Result.Failure -> isEmpty.set(true)
            }
        }
    }


    // MARK: - Adapter

    private class LessonListAdapter (
            var data: List<Pair<Lesson, Memo?>>,
            var onItemClick: ((Lesson) -> Unit)?
    ) : RecyclerView.Adapter<LessonListAdapter.LessonViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): LessonViewHolder {

            val inflater = LayoutInflater.from(parent?.context)
            val binding = ItemLessonCardBinding.inflate(inflater, parent, false)
            return LessonViewHolder(binding)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: LessonViewHolder?, position: Int) {

            val lesson = data[position].first
            holder?.binding?.lesson = lesson
            holder?.binding?.memo = data[position].second
            holder?.binding?.root?.setOnClickListener { onItemClick?.invoke(lesson) }
        }

        class LessonViewHolder(var binding: ItemLessonCardBinding) : RecyclerView.ViewHolder(binding.root)
    }


    companion object {

        private const val ARG_DAY_OF_WEEK = "DAY_OF_WEEK"

        fun newInstance(day: DayOfWeek): LessonListFragment = LessonListFragment().apply {

            arguments = bundleOf(ARG_DAY_OF_WEEK to day.ordinal)
        }
    }
}// Required empty public constructor
