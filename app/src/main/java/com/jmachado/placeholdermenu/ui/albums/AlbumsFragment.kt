package com.jmachado.placeholdermenu.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jmachado.placeholdermenu.MainApplication
import com.jmachado.placeholdermenu.adapters.AlbumAdapter
import com.jmachado.placeholdermenu.databinding.FragmentAlbumsBinding
import com.jmachado.placeholdermenu.model.Album

class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter:AlbumAdapter
    private val viewModel: AlbumsViewModel by viewModels {
        AlbumsViewModelFactory(( activity?.application as MainApplication).albumRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        adapter = AlbumAdapter{ entity -> adapterOnClick(entity)}
        binding.albumRecyclerView.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adapterOnClick(entity: Album) {
        Toast.makeText(context, "Selected: ${entity.title}", Toast.LENGTH_SHORT).show()
    }

    private fun subscribeUi(adapter: AlbumAdapter) {
        viewModel.allAlbums.observe(viewLifecycleOwner) { entity ->
            adapter.submitList(entity)
        }

    }
}