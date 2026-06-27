package com.example.wxforecastui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.wxforecastui.mock.MockWeatherDataSource
import com.example.wxforecastui.repository.WeatherRepository
import com.example.wxforecastui.api.OpenMeteoService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var useFahrenheit = false
    private var weatherReport = HourlyWeatherReport(
        city = MockWeatherDataSource.city,
        temperatureCelsius = MockWeatherDataSource.getWeather().current.temperature,
        condition = "Clear Sky",
        forecastTime = MockWeatherDataSource.forecastTime
    )

    private lateinit var tvCity: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvLastUpdated: TextView
    private lateinit var btnToggleUnit: MaterialButton
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var repository: WeatherRepository

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) fetchLocation()
        else updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvCity = findViewById(R.id.tvCity)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvCondition = findViewById(R.id.tvCondition)
        tvLastUpdated = findViewById(R.id.tvLastUpdated)
        btnToggleUnit = findViewById(R.id.btnToggleUnit)

        setupRetrofit()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        updateUI()
        requestLocation()

        btnToggleUnit.setOnClickListener {
            useFahrenheit = !useFahrenheit
            updateUI()
        }
    }

    private fun setupRetrofit() {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        repository = WeatherRepository(retrofit.create(OpenMeteoService::class.java))
    }

    private fun requestLocation() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation()
        } else {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun fetchLocation() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val locationRequest = com.google.android.gms.location.CurrentLocationRequest.Builder()
            .setPriority(com.google.android.gms.location.Priority.PRIORITY_LOW_POWER)
            .build()

        fusedLocationClient.getCurrentLocation(locationRequest, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    val city = if (!addresses.isNullOrEmpty()) {
                        "${addresses[0].locality}, ${addresses[0].countryName}"
                    } else {
                        MockWeatherDataSource.city
                    }
                    fetchWeather(location.latitude, location.longitude, city)
                } else {
                    updateUI()
                }
            }
    }

    private fun fetchWeather(latitude: Double, longitude: Double, city: String) {
        lifecycleScope.launch {
            val response = repository.getWeather(latitude, longitude)
            val condition = com.example.wxforecastui.util.WmoCodeMapper.getCondition(
                response.current.weathercode
            )
            val forecastTime = Calendar.getInstance().apply {
                set(Calendar.MINUTE, 0)
            }.time
            weatherReport = HourlyWeatherReport(
                city = city,
                temperatureCelsius = response.current.temperature,
                condition = condition,
                forecastTime = forecastTime
            )
            updateUI()
        }
    }

    private fun updateUI() {
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        tvCity.text = weatherReport.city
        tvTemperature.text = weatherReport.formatTemperatureDisplay(useFahrenheit)
        tvCondition.text = weatherReport.condition
        tvLastUpdated.text = "Last updated: ${formatter.format(weatherReport.forecastTime)}"
        btnToggleUnit.text = if (useFahrenheit) "Switch to °C" else "Switch to °F"
    }
}