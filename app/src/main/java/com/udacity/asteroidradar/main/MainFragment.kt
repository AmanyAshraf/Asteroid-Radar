package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
   // private val viewModel: MainViewModel by  activityViewModels()
   private val viewModel: MainViewModel by lazy {
       ViewModelProvider(this).get(MainViewModel::class.java)
   }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        val adapter = AsteroidAdapter(AsteroidClickListener { asteroidId ->
            viewModel.onAsteroidItemClick(asteroidId)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.asteroids.observe(viewLifecycleOwner, { asteroids ->
            adapter.submitList(asteroids)
        })
        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner,  { asteroid ->
            asteroid?.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.onDetailFragmentNavigated()
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.onChangeFilter(
            when (item.itemId){
                R.id.show_rent_menu -> AsteroidFilter.TODAY
                R.id.show_all_menu ->AsteroidFilter.WEEK
                else -> AsteroidFilter.ALL
            }
        )
        return true
    }
}
