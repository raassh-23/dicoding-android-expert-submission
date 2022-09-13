package com.raassh.myfavmovies.detail

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.raassh.core.ui.model.Movie
import com.raassh.core.utils.loadImage
import com.raassh.core.utils.withDateFormat
import com.raassh.myfavmovies.R
import com.raassh.myfavmovies.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    val viewModel by viewModel<DetailViewModel>()
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
        binding?.apply {
            tvTitle.text = movie.title
            ivImage.loadImage(movie.posterPath)
            tvReleaseDate.text = movie.releaseDate.withDateFormat()
            tvRating.text = if (movie.voteCount == 0) {
                getString(com.raassh.core.R.string.no_vote)
            } else {
                getString(R.string.rating, movie.voteAverage.toString(), movie.voteCount)
            }
            tvSynopsis.text = movie.overview
        }

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.option_menu_detail, menu)
                setFavoriteIcon(menu.findItem(R.id.favorite), movie)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.favorite -> {
                        viewModel.setFavoriteMovie(movie)
                        setFavoriteIcon(menuItem, movie)
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun setFavoriteIcon(menuItem: MenuItem, movie: Movie) {
        menuItem.setIcon(
            if (movie.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}