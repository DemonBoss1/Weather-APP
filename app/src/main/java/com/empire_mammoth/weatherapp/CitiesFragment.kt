package com.empire_mammoth.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.fragment.app.Fragment
import com.empire_mammoth.weatherapp.databinding.FragmentCitiesBinding

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CitiesAdapter
    private val viewModel: CitiesViewModel by viewModels()

    companion object {
        fun newInstance() = CitiesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupRecyclerView()
//        setupClickListeners()
//        setupObservers()
//        viewModel.loadCities()
//    }
//
//    private fun setupRecyclerView() {
//        adapter = CitiesAdapter().apply {
//            onItemClick = { city ->
//                viewModel.setSelectedCity(city)
//                (activity as? MainActivity)?.navigateTo(
//                    WeatherFragment.newInstance(),
//                    addToBackStack = false
//                )
//            }
//        }
//
//        with(binding.rvCities) {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = this@CitiesFragment.adapter
//            addItemDecoration(
//                DividerItemDecoration(
//                    requireContext(),
//                    LinearLayoutManager.VERTICAL
//                )
//            )
//        }
//    }
//
//    private fun setupClickListeners() {
//        binding.fabAddCity.setOnClickListener {
//            showAddCityDialog()
//        }
//    }
//
//    private fun setupObservers() {
//        viewModel.citiesList.observe(viewLifecycleOwner) { cities ->
//            adapter.submitList(cities)
//        }
//    }
//
//    private fun showAddCityDialog() {
//        // Реализация диалога добавления города
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}