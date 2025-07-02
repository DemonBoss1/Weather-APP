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
import com.empire_mammoth.weatherapp.data.models.ForecastDay
import com.empire_mammoth.weatherapp.data.models.WeatherResponse
import com.empire_mammoth.weatherapp.data.models.HourForecast
import com.empire_mammoth.weatherapp.databinding.FragmentWeatherBinding
import com.empire_mammoth.weatherapp.databinding.ItemForecastDayBinding
import com.empire_mammoth.weatherapp.databinding.ItemHourlyForecastBinding
import com.empire_mammoth.weatherapp.presentation.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.empire_mammoth.weatherapp.domain.model.WeatherState.WeatherState

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        binding.tvFeelsLike.text = "Feels like --°C | --°F"

        // Детали погоды
        binding.tvHumidity.text = "--%"
        binding.tvWind.text = "-- km/h"
        binding.tvPressure.text = "-- hPa | -- in"
        binding.tvUV.text = "--"
        binding.tvVisibility.text = "-- km | -- miles"
        binding.tvCloud.text = "--%"

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

    private fun updateUI(response: WeatherResponse) {
        // Заполняем данные о местоположении
        with(response.location) {
            binding.tvLocation.text = name
            binding.tvRegion.text = "$region, \n$country"
        }

        // Заполняем текущую погоду
        with(response.current) {
            // Форматируем дату и время
            val lastUpdated = SimpleDateFormat("MMMM dd, h:mm a", Locale.getDefault())
                .format(Date(lastUpdatedEpoch * 1000))
            binding.tvDateTime.text = "$lastUpdated | Last updated: ${lastUpdated.substring(11)}"

            // Загружаем иконку погоды
            Glide.with(this@WeatherFragment)
                .load("https:${condition.icon}")
                .into(binding.ivWeatherIcon)

            binding.tvTemperature.text = "${tempC.toInt()}°C | ${tempF.toInt()}°F"
            binding.tvCondition.text = condition.text
            binding.tvFeelsLike.text =
                "Feels like ${feelslikeC.toInt()}°C | ${feelslikeF.toInt()}°F"
        }

        // Заполняем детали погоды
        with(response.current) {
            binding.tvHumidity.text = "$humidity%"
            binding.tvWind.text = "$windKph km/h \n$windDir ($windDegree°)"
            binding.tvPressure.text = "$pressureMb hPa \n${"%.2f".format(pressureIn)} in"
            binding.tvUV.text = "$uv (${getUvIndexLevel(uv)})"
            binding.tvVisibility.text = "$visKm km \n ${"%.1f".format(visMiles)} miles"
            binding.tvCloud.text = "$cloud%"
        }

        // Заполняем прогноз на 3 дня
        val forecastDays = response.forecast.forecastDays.take(3)
        forecastDays.forEachIndexed { index, forecastDay ->
            when (index) {
                0 -> setupForecastDay(binding.forecastToday.root, forecastDay, "Today")
                1 -> setupForecastDay(binding.forecastTomorrow.root, forecastDay, "Tomorrow")
                2 -> setupForecastDay(
                    binding.forecastDayAfter.root,
                    forecastDay,
                    getDayName(forecastDay.date)
                )
            }
        }

        // Заполняем почасовой прогноз
        setupHourlyForecast(response.forecast.forecastDays.first().hours)

        // Заполняем астрономические данные
        with(response.forecast.forecastDays.first().astro) {
            binding.tvSunrise.text = sunrise
            binding.tvSunset.text = sunset
            binding.tvMoonrise.text = moonrise
            binding.tvMoonPhase.text = "$moonPhase (${moonIllumination}%)"
        }
    }

    private fun setupForecastDay(view: View, forecastDay: ForecastDay, dayName: String) {
        val dayBinding = ItemForecastDayBinding.bind(view)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(forecastDay.date)
        val displayFormat = SimpleDateFormat("MMMM d", Locale.getDefault())

        dayBinding.tvDateItemForecastDay.text = "$dayName, \n${displayFormat.format(date)}"
        dayBinding.tvTempItemForecastDay.text =
            "${forecastDay.day.maxTempC.toInt()}° / ${forecastDay.day.minTempC.toInt()}°"
        dayBinding.tvConditionItemForecastDay.text = forecastDay.day.condition.text

        // Загружаем иконку
        Glide.with(this)
            .load("https:${forecastDay.day.condition.icon}")
            .into(dayBinding.ivWeatherIconItemForecastDay)
    }

    private fun setupHourlyForecast(hours: List<HourForecast>) {
        binding.hourlyForecastContainer.removeAllViews()

        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val relevantHours = hours.filter {
            it.time.substring(11, 13).toInt() >= currentHour
        }.take(12) // Показываем 12 следующих часов

        relevantHours.forEach { hour ->
            val hourBinding = ItemHourlyForecastBinding.inflate(layoutInflater)

            hourBinding.tvHour.text = if (hour.time.substring(11, 13).toInt() == currentHour) {
                "Now"
            } else {
                hour.time.substring(11, 16)
            }

            hourBinding.tvHourlyTemp.text = "${hour.tempC.toInt()}°"

            Glide.with(this)
                .load("https:${hour.condition.icon}")
                .into(hourBinding.ivHourlyIcon)

            binding.hourlyForecastContainer.addView(hourBinding.root)
        }
    }

    private fun getDayName(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(date)
    }

    private fun getUvIndexLevel(uv: Double): String {
        return when {
            uv < 3 -> "Low"
            uv < 6 -> "Moderate"
            uv < 8 -> "High"
            uv < 11 -> "Very High"
            else -> "Extreme"
        }
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