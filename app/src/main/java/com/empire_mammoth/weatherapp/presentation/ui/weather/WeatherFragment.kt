package com.empire_mammoth.weatherapp.presentation.ui.weather

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.empire_mammoth.weatherapp.R
import com.empire_mammoth.weatherapp.databinding.FragmentWeatherBinding
import com.empire_mammoth.weatherapp.databinding.ItemForecastDayBinding
import com.empire_mammoth.weatherapp.databinding.ItemHourlyForecastBinding
import com.empire_mammoth.weatherapp.domain.model.DailyForecastUI
import com.empire_mammoth.weatherapp.domain.model.HourlyForecastUI
import com.empire_mammoth.weatherapp.domain.model.WeatherDataUI
import com.empire_mammoth.weatherapp.domain.model.WeatherState.WeatherState
import com.empire_mammoth.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Устанавливаем значения по умолчанию
        setDefaultValues()

        collectWeatherState()
        viewModel.getWeather("London")
    }

    private fun setDefaultValues() {
        // Местоположение
        binding.tvLocation.text = "Нет данных"
        binding.tvRegion.text = "Неизвестный регион"

        // Текущая погода
        binding.tvDateTime.text = "Данные не загружены"
        binding.tvTemperature.text = "--°C | --°F"
        binding.tvCondition.text = "Нет данных"
        binding.tvFeelsLike.text = "Feels like --°C"

        // Детали погоды
        binding.tvHumidity.text = "--%"
        binding.tvWind.text = "-- km/h"

        // Астрономические данные
        binding.tvSunrise.text = "--:--"
        binding.tvSunset.text = "--:--"
        binding.tvMoonrise.text = "--:--"
        binding.tvMoonPhase.text = "Нет данных"

        // Прогноз на 3 дня
        setupDefaultForecast(binding.forecastToday.root, "Today")
        setupDefaultForecast(binding.forecastTomorrow.root, "Tomorrow")
        setupDefaultForecast(binding.forecastDayAfter.root, "--")

        // Почасовой прогноз
        setupDefaultHourlyForecast()
    }

    private fun setupDefaultForecast(view: View, dayName: String) {
        val dayBinding = ItemForecastDayBinding.bind(view)
        dayBinding.tvDateItemForecastDay.text = dayName
        dayBinding.tvTempItemForecastDay.text = "--° / --°"
        dayBinding.tvConditionItemForecastDay.text = "Нет данных"
        dayBinding.ivWeatherIconItemForecastDay.setImageResource(R.drawable.ic_unknown)
    }

    private fun setupDefaultHourlyForecast() {
        binding.hourlyForecastContainer.removeAllViews()

        val hourBinding = ItemHourlyForecastBinding.inflate(layoutInflater)
        hourBinding.tvHour.text = "Now"
        hourBinding.tvHourlyTemp.text = "--°"
        hourBinding.ivHourlyIcon.setImageResource(R.drawable.ic_unknown)
        binding.hourlyForecastContainer.addView(hourBinding.root)
    }

    private fun collectWeatherState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherState.collect { state ->
                    when (state) {
                        is WeatherState.Loading -> showLoading()
                        is WeatherState.Success -> {
                            hideLoading()
                            updateUI(state.data)
                        }

                        is WeatherState.Error -> {
                            hideLoading()
                            showError(state.message)
                            setDefaultValues() // Устанавливаем значения по умолчанию при ошибке
                        }
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun updateUI(weatherData: WeatherDataUI) {
        // Заполняем данные о местоположении
        with(weatherData.current) {
            binding.tvLocation.text = location
            binding.tvRegion.text = region
        }

        // Заполняем текущую погоду
        with(weatherData.current) {
            binding.tvDateTime.text = "$lastUpdated | Last updated: ${lastUpdated.substring(11)}"

            Glide.with(this@WeatherFragment)
                .load(iconUrl)
                .into(binding.ivWeatherIcon)

            binding.tvTemperature.text = "${temperature.toInt()}°C"
            binding.tvCondition.text = condition
            binding.tvFeelsLike.text = "Feels like ${feelsLike.toInt()}°C"
        }

        // Заполняем детали погоды
        with(weatherData.current) {
            binding.tvHumidity.text = "$humidity%"
            binding.tvWind.text = "$windSpeed km/h\n$windDirection"
        }

        // Заполняем прогноз на 3 дня
        weatherData.dailyForecast.take(3).forEachIndexed { index, dailyForecast ->
            when (index) {
                0 -> setupForecastDay(binding.forecastToday.root, dailyForecast, "Today")
                1 -> setupForecastDay(binding.forecastTomorrow.root, dailyForecast, "Tomorrow")
                2 -> setupForecastDay(
                    binding.forecastDayAfter.root,
                    dailyForecast,
                    getDayName(dailyForecast.date)
                )
            }
        }

        // Заполняем почасовой прогноз
        setupHourlyForecast(weatherData.hourlyForecast)

        // Заполняем астрономические данные
        with(weatherData.astronomy) {
            binding.tvSunrise.text = sunrise
            binding.tvSunset.text = sunset
            binding.tvMoonrise.text = moonrise
            binding.tvMoonPhase.text = moonPhase
        }
    }

    private fun setupForecastDay(view: View, forecast: DailyForecastUI, dayName: String) {
        val dayBinding = ItemForecastDayBinding.bind(view)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(forecast.date)
        val displayFormat = SimpleDateFormat("MMMM d", Locale.getDefault())

        dayBinding.tvDateItemForecastDay.text = "$dayName,\n${displayFormat.format(date)}"
        dayBinding.tvTempItemForecastDay.text =
            "${forecast.maxTemp.toInt()}° / ${forecast.minTemp.toInt()}°"
        dayBinding.tvConditionItemForecastDay.text = forecast.condition

        Glide.with(this)
            .load(forecast.iconUrl)
            .into(dayBinding.ivWeatherIconItemForecastDay)
    }

    private fun setupHourlyForecast(hours: List<HourlyForecastUI>) {
        binding.hourlyForecastContainer.removeAllViews()

        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val relevantHours = hours.filter {
            it.time.substring(0, 2).toInt() >= currentHour
        }.take(12)

        relevantHours.forEach { hour ->
            val hourBinding = ItemHourlyForecastBinding.inflate(layoutInflater)

            hourBinding.tvHour.text = if (hour.time.substring(0, 2).toInt() == currentHour) {
                "Now"
            } else {
                hour.time
            }

            hourBinding.tvHourlyTemp.text = "${hour.temp.toInt()}°"

            Glide.with(this)
                .load(hour.iconUrl)
                .into(hourBinding.ivHourlyIcon)

            binding.hourlyForecastContainer.addView(hourBinding.root)
        }
    }

    // Вспомогательные функции
    private fun getDayName(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.scrollView.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE
    }
}