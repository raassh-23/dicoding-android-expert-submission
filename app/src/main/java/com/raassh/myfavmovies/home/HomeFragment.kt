package com.raassh.myfavmovies.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.raassh.core.data.Resource
import com.raassh.core.ui.MovieAdapter
import com.raassh.myfavmovies.R
import com.raassh.myfavmovies.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    val viewModel by viewModel<HomeViewModel>()

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MovieAdapter().apply {
            onItemClick = {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            }
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding?.apply {
                            progressBar.visibility = View.VISIBLE
                            rvMovies.visibility = View.GONE
                            tvError.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        binding?.apply {
                            progressBar.visibility = View.GONE
                            rvMovies.visibility = View.VISIBLE

                            if (it.data.isNullOrEmpty()) {
                                tvError.visibility = View.VISIBLE
                                tvError.text = getString(R.string.not_found)
                            }
                        }

                        adapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        binding?.apply {
                            progressBar.visibility = View.GONE
                            tvError.visibility = View.VISIBLE
                            tvError.text = it.message
                        }
                    }
                }
            }
        }

        binding?.rvMovies?.adapter = adapter

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu)

                val searchManager =
                    menuHost.getSystemService(Context.SEARCH_SERVICE) as SearchManager
                val searchMenuItem = menu.findItem(R.id.search)
                val searchView = searchMenuItem.actionView as SearchView

                searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                        return true
                    }

                    override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                        viewModel.setQuery("")
                        return true
                    }
                })

                searchView.apply {
                    setSearchableInfo(searchManager.getSearchableInfo(menuHost.componentName))
                    queryHint = resources.getString(R.string.search_hint)
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String): Boolean {
                            viewModel.setQuery(query)
                            searchView.clearFocus()
                            return true
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            return false
                        }
                    })
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.favorite -> {
                        // to move to feature modules with navigation component,
                        // see https://developer.android.com/guide/navigation/navigation-dynamic
                        findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.setQuery("")
    }

}