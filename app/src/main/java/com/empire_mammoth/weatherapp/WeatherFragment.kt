package com.empire_mammoth.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.empire_mammoth.weatherapp.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        setupObservers()
//        setupClickListeners()
//        viewModel.loadWeather()
//    }
//
//    private fun setupObservers() {
//        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
//            with(binding) {
//                tvCity.text = weather.cityName
//                tvTemperature.text = "${weather.temperature}°C"
//                tvCondition.text = weather.condition
//                ivWeatherIcon.setImageResource(getWeatherIcon(weather.condition))
//                // Обновляем другие данные
//            }
//        }
//    }
//
//    private fun setupClickListeners() {
//        binding.btnCities.setOnClickListener {
//            (activity as? MainActivity)?.navigateTo(CitiesFragment.newInstance())
//        }
//    }
//
//    private fun getWeatherIcon(condition: String): Int {
//        return when (condition.toLowerCase()) {
//            "sunny" -> R.drawable.ic_sun
//            "cloudy" -> R.drawable.ic_cloud
//            else -> R.drawable.ic_cloud
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}