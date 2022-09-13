package com.raassh.myfavmovies.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.raassh.core.ui.MovieAdapter
import com.raassh.myfavmovies.favorite.databinding.FragmentFavoriteBinding
import com.raassh.myfavmovies.home.HomeFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {
    private val viewModel by viewModel<FavoriteViewModel>()

    private var binding: FragmentFavoriteBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(favoriteModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieAdapter().apply {
            onItemClick = {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }

        viewModel.favMovies.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding?.tvEmpty?.visibility = View.GONE
                adapter.setData(it)
            } else {
                binding?.tvEmpty?.visibility = View.VISIBLE
            }
        }

        binding?.rvMovies?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}