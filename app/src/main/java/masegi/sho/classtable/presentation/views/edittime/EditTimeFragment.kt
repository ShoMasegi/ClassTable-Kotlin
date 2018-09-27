package masegi.sho.classtable.presentation.views.edittime

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.data.Prefs

import masegi.sho.classtable.databinding.FragmentEditTimeBinding
import masegi.sho.classtable.databinding.ItemEditTimeBinding
import masegi.sho.classtable.kotlin.data.model.Time
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject

class EditTimeFragment : DaggerFragment() {


    // MARK: - Property

    private lateinit var binding: FragmentEditTimeBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditTimeViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(EditTimeViewModel::class.java)
    }
    private lateinit var adapter: LessonTimeAdapter
    private var timesMap: MutableMap<Int, Time> = mutableMapOf()
    private var addedTime = false


    // MARK: - Fragment

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        adapter = LessonTimeAdapter(timesMap) { time -> showTimePickerDialog(time) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentEditTimeBinding.inflate(inflater, container!!, false)
        binding.recyclerView.run {

            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = this@EditTimeFragment.adapter
            addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
        viewModel.times.observe(this) { result ->

            when (result) {

                is Result.Success -> {

                    timesMap.clear()
                    timesMap.putAll(result.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        return binding.root
    }

    override fun onPause() {

        super.onPause()
        if (addedTime) viewModel.save(timesMap)
    }


    // MARK: - Private

    private fun showTimePickerDialog(time: Time) {

        fragmentManager?.let {

            TimePickerDialogFragment.Builder(time)
                .setOnSetTimeListener {

                    timesMap[time.periodNum] = time
                    adapter.notifyItemChanged(time.periodNum - 1)
                    addedTime = true
                }
                .show(it)
        }
    }


    // MARK: - LessonTimeAdapter

    private class LessonTimeAdapter(
            var times: Map<Int, Time>,
            var onTimeClick: ((Time) -> Unit)? = null
    ) : androidx.recyclerview.widget.RecyclerView.Adapter<LessonTimeAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemEditTimeBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int = Prefs.dayLessonCount

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val time = times[position + 1] ?: Time(tid = Prefs.tid, periodNum = position + 1)
            holder.let {

                it.binding.time = time
                it.binding.root.setOnClickListener { onTimeClick?.invoke(time) }
            }
        }

        class ViewHolder(var binding: ItemEditTimeBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)
    }

    companion object {

        fun newInstance(): EditTimeFragment = EditTimeFragment()
    }
}// Required empty public constructor
