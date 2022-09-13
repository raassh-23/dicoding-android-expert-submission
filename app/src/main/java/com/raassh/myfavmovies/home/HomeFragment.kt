package com.raassh.myfavmovies.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
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
                when(it) {
                    is Resource.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                        binding?.rvMovie?.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.rvMovie?.visibility = View.VISIBLE
                        adapter.setData(it.data)
                    }
                    is Resource.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_INDEFINITE).show()
                    }
                }
            }
        }

        binding?.rvMovie?.adapter = adapter

        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu)

                val searchManager = menuHost.getSystemService(Context.SEARCH_SERVICE) as SearchManager
                val menuItem = menu.findItem(R.id.search)
                val searchView = menuItem.actionView as SearchView

                menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
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
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}