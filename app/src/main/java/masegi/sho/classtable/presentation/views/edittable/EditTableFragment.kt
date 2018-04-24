package masegi.sho.classtable.presentation.views.edittable

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dagger.android.support.DaggerFragment
import masegi.sho.classtable.R
import masegi.sho.classtable.data.Prefs
import masegi.sho.classtable.data.model.PrefEntity
import masegi.sho.classtable.databinding.DialogCreateTableBinding

import masegi.sho.classtable.databinding.FragmentEditTableBinding
import masegi.sho.classtable.presentation.Result
import masegi.sho.classtable.utli.ext.observe
import javax.inject.Inject

class EditTableFragment : DaggerFragment() {


    // MARK: - Property

    private var prefs: List<PrefEntity> = listOf()
        set(value) {

            field = value
            val map = mutableMapOf<Int, String>()
            value.forEach { map[it.tid] = it.name }
            _tables = map
        }
    private var _tables: Map<Int, String> = mapOf()
        set(value) {

            field = value
            adapter = ArrayAdapter(
                    context,
                    android.R.layout.simple_list_item_1,
                    _tables.values.toList().sortedBy { id }
            )
            binding.listView.adapter = adapter
        }
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var binding: FragmentEditTableBinding
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: EditTableViewModel by lazy {

        ViewModelProviders.of(this, viewModelFactory).get(EditTableViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentEditTableBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.prefs.observe(this) { result ->

            when (result) {

                is Result.Success -> prefs = result.data
            }
        }
    }


    // MARK: - Private

    private fun setupViews() {

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            prefs.firstOrNull { it.name == adapter.getItem(position) }?.let {

                showOptionsMenu(it)
            }
        }
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {

            showEditTableNameDialog(null)
        }
    }

    private fun showEditTableNameDialog(pref: PrefEntity?) {

        val data: PrefEntity = pref ?: PrefEntity(name = "")
        val binding = DialogCreateTableBinding.inflate(LayoutInflater.from(context), null)
        binding.editText.setText(data.name)
        AlertDialog.Builder(context!!)
                .setTitle(R.string.dialog_table_name)
                .setView(binding.root)
                .setPositiveButton(android.R.string.ok) { _, _ ->

                    val pref = prefs.firstOrNull { it.name == binding.editText.text.toString() }
                    when (pref) {
                        null -> {

                            data.name = binding.editText.text.toString()
                            viewModel.insert(data)
                        }
                        data -> { }
                        else -> showErrorMessage(R.string.error_save_same_name_table)
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
    }

    private fun showOptionsMenu(pref: PrefEntity) {

        AlertDialog.Builder(context!!)
                .setTitle(pref.name)
                .setItems(R.array.edit) { _, which ->

                    if (which == 0) {

                        showEditTableNameDialog(pref)
                    }
                    else {

                        showDeleteConfirmDialog(pref)
                    }
                }
                .show()
    }

    private fun showDeleteConfirmDialog(pref: PrefEntity) {

        AlertDialog.Builder(context!!)
                .setTitle(R.string.dialog_table_delete)
                .setMessage(R.string.dialog_table_delete_message)
                .setPositiveButton("delete") { _, _ ->

                    if (Prefs.tid == pref.tid) {

                        showErrorMessage(R.string.error_delete_table)
                    }
                    else {

                        viewModel.delete(pref)
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
    }

    private fun showErrorMessage(@StringRes message: Int) {
        AlertDialog.Builder(context!!)
                .setTitle(R.string.dialog_error)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }

    companion object {

        internal fun newInstance(): EditTableFragment = EditTableFragment()
    }
}// Required empty public constructor
