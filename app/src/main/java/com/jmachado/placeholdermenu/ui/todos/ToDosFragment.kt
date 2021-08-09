package com.jmachado.placeholdermenu.ui.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jmachado.placeholdermenu.MainApplication
import com.jmachado.placeholdermenu.adapters.TodosAdapter
import com.jmachado.placeholdermenu.databinding.FragmentTodosBinding
import com.jmachado.placeholdermenu.model.Todos

class ToDosFragment : Fragment() {

    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter:TodosAdapter
    private val viewModel: ToDosViewModel by viewModels {
        ToDosViewModelFactory(( activity?.application as MainApplication).todosRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentTodosBinding.inflate(inflater, container, false)
        adapter = TodosAdapter{ entity -> adapterOnClick(entity)}
        binding.todosRecyclerView.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterOnClick(entity: Todos) {
        Toast.makeText(context, "Selected: ${entity.title}", Toast.LENGTH_SHORT).show()
    }

    private fun subscribeUi(adapter: TodosAdapter) {
        viewModel.allTodos.observe(viewLifecycleOwner) { entity ->
            adapter.submitList(entity)
        }

    }
}