# WXForecastUI

A weather forecast Android app built with Kotlin and Material Design 3, 
developed as part of CS 4405 – Mobile Applications at the University of the People.

## Features
- Displays city, temperature, weather condition, and last updated time
- Toggle between Celsius and Fahrenheit
- Material Design 3 theming with dynamic color support (Android 12+)
- Light and dark mode variants

## Architecture
- `WeatherReport` — base class holding current conditions
- `HourlyWeatherReport` — subclass responsible for time-specific forecast data,
  populating the last updated timestamp. Architecturally designed to support 
  future enhancements such as scheduled data refresh and live API integration.
- Mock data source with a clean separation of concerns to facilitate future extension

## Testing
- JUnit unit tests for Celsius/Fahrenheit conversion in both directions
- Reference points: freezing (0°C/32°F) and boiling (100°C/212°F)

## Branch Structure
- `master` — final version of the base assignment submission
- `extended` — enhanced version with additional features (in progress)

## Notes
This is the final version of the base application. Further architectural and UI 
enhancements are planned and documented in the `extended` branch and the 
accompanying written assignment.
