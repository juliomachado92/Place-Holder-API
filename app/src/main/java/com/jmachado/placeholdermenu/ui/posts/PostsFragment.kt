package com.jmachado.placeholdermenu.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jmachado.placeholdermenu.MainApplication
import com.jmachado.placeholdermenu.adapters.PostAdapter
import com.jmachado.placeholdermenu.databinding.FragmentPostsBinding
import com.jmachado.placeholdermenu.model.Post

class PostsFragment : Fragment() {

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:PostAdapter

    private val viewModel: PostsViewModel by viewModels {
        PostsViewModelFactory(( activity?.application as MainApplication).postRepository)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        _binding = FragmentPostsBinding.inflate(inflater, container, false)

        adapter = PostAdapter{ entity -> adapterOnClick(entity)}
        binding.postRecyclerView.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterOnClick(entity: Post) {
        Toast.makeText(context, "Selected: ${entity.title}", Toast.LENGTH_SHORT).show()
    }

    private fun subscribeUi(adapter: PostAdapter) {
        viewModel.allPosts.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

    }
}