package com.raassh.myfavmovies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raassh.myfavmovies.databinding.FragmentDetailBinding
import com.raassh.myfavmovies.databinding.FragmentHomeBinding

class DetailFragment : Fragment() {

    val viewModel by viewModels<DetailViewModel>()
    private var binding: FragmentDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = DetailFragmentArgs.fromBundle(requireArguments()).movie
        binding?.tvTitle?.text = movie.title

        activity?.actionBar?.apply {
            title = movie.title
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}