package com.empire_mammoth.weatherapp.presentation.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.empire_mammoth.weatherapp.databinding.FragmentCityListBinding
import com.empire_mammoth.weatherapp.domain.model.CityListState
import com.empire_mammoth.weatherapp.presentation.adapter.CityAdapter
import com.empire_mammoth.weatherapp.presentation.viewmodel.CityListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CityListFragment : Fragment() {

    private lateinit var binding: FragmentCityListBinding
    private val viewModel: CityListViewModel by viewModels()
    private lateinit var adapter: CityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeState()
    }

    private fun setupRecyclerView() {
        adapter = CityAdapter { city ->
            viewModel.selectCity(city.id)
            findNavController().navigateUp()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CityListFragment.adapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is CityListState.Loading -> showLoading()
                        is CityListState.Success -> {
                            hideLoading()
                            adapter.submitList(state.cities)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}