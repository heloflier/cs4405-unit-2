# WXForecastUI — Extended Branch

An enhanced version of the WXForecastUI weather forecast Android app, 
building on the base assignment submission in `master`.

## Status
Work in progress. See `master` for the completed base version.

## Features (completed)
- All features from `master`
- Live weather data via Open-Meteo API (no API key required)
- Current device location via FusedLocationProviderClient
- Reverse geocoding via Android Geocoder
- Mock data fallback on API failure or location denial
- Repository pattern separating data concerns from UI

## In Progress
- Step 4: Timed background refresh + manual refresh IconButton
- Step 5: MD3 weather condition icons
- Step 6: Light/Dark mode toggle via AppCompatDelegate
- Step 7: Espresso UI tests

## Architecture
- `WeatherReport` — base class, owns current conditions
- `HourlyWeatherReport` — subclass, owns time dimension via `forecastTime`
- `WeatherRepository` — fetches from API, falls back to mock on failure
- `MockWeatherDataSource` — hardcoded fallback data
- `OpenMeteoService` — Retrofit interface for Open-Meteo API
- `WmoCodeMapper` — maps WMO weather codes to condition strings
- `WXForecastApplication` — enables MD3 dynamic color support

## Package Structure
com.example.wxforecastui
├── api/          — Retrofit service and response models
├── mock/         — Mock data source
├── repository/   — Data access layer
└── util/         — WMO code mapping

## Branch Structure
- `master` — final base assignment submission
- `extended` — this branch, enhanced version in progress

## Testing
- JUnit unit tests for temperature conversion (both directions)
- Espresso UI tests planned for Step 7