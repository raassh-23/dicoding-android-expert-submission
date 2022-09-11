package com.raassh.myfavmovies.listmovie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.raassh.core.data.Resource
import com.raassh.core.ui.MovieAdapter
import com.raassh.myfavmovies.R
import com.raassh.myfavmovies.databinding.FragmentListMovieBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMovieFragment : Fragment() {

    val viewModel by viewModel<ListMovieViewModel>()

    private var binding: FragmentListMovieBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentListMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieAdapter().apply {
            onItemClick = {
                Log.d("TAG", "onViewCreated: $it")
            }
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            if (it != null) {
                when(it) {
                    is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        Log.d("TAG", "onViewCreated: ${it.data}")
                        adapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding?.rvMovie?.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}