# 🌦️ Android Weather Forecast App

An Android application that displays current weather conditions and multi-hour forecasts based on parsed JSON weather data. This app reads weather information (such as temperature, humidity, wind speed, and icon codes) from an API response passed through an Intent, then renders a detailed and scrollable forecast.

## 📱 Features

<img width="200" height="2400" alt="image" src="https://github.com/user-attachments/assets/952b3e5f-3355-40b7-b8d5-b623dfb56e15" />
<img width="200" height="2400" alt="image" src="https://github.com/user-attachments/assets/d67e117b-5291-49d0-94e1-54e62bd4393d" />
<img width="200" height="2400" alt="image" src="https://github.com/user-attachments/assets/4dd18ee8-36fa-41ef-b8d7-dbeb47b54252" />
<img width="200" height="2400" alt="image" src="https://github.com/user-attachments/assets/59b9e206-c571-489c-bd7a-133c0a49eb30" />

## 🌤 Current Weather Overview

Displays real-time weather information for the selected city including:

Current temperature

High & low for the day

Feels-like temperature

Weather description

Humidity

Wind speed

Sunrise-friendly weather icons

Formatted date & time

Latitude & longitude of location

## 📅 Forecast List

A scrollable ListView showing upcoming forecasts, each with:

Converted date + time

Temperature

High & low

Wind speed

Feels-like temperature

Humidity

Weather description

Associated weather icon

## 🖼️ Icon Support

Weather icon codes (e.g., "01d", "03n") are mapped to drawable resources:

Sunny

Night clear

Cloudy

Scattered clouds

Rain

Thunderstorm

Snow

Fog / Mist

## 🧠 Robust JSON Parsing

The app processes the full OpenWeather-style response:

Extracts metadata: city → name, coordinates

Reads the forecast array "list"

Formats and stores each forecast entry in a custom Weather object

## 🕒 Readable Time Formatting

The app converts "dt_txt" timestamps such as:

2024-03-05 18:00:00


into:

6 PM | Mar 5


or for forecast list items:

6 PM Mar 5

## 🧩 Code Architecture
Main Components
File/Class	Responsibility
MainActivityWeather.java	Main controller for parsing JSON, populating UI, and loading forecast list
Weather.java	Model class storing parsed forecast data
CustomAdapter.java	Adapter for formatting forecast rows in the ListView
activity_main_weather.xml	Layout for main weather display
adapter_layout.xml	Layout for each forecast list item
Drawables	Weather icon images

## 🔧 How It Works (Detailed)
1. Receive JSON Weather Data

Data is passed from another Activity using:

getIntent().getStringExtra("Zip Code");


This string contains the full API response.

2. Parse the JSON

Extract top-level fields:

object.getJSONObject("city").getString("name")
object.getJSONObject("city").getJSONObject("coord").getDouble("lat")


Then iterate through "list" to build forecast entries.

3. Format Weather Values

Temperatures are rounded

Timestamps are converted to 12-hour AM/PM format

Month numbers converted to abbreviations

Weather descriptions are capitalized correctly

4. Visualize Data

Set main weather panel text values

Select icon using:

choosePicture(iconCode)


Populate ListView using CustomAdapter

## 🛠 Technologies Used

Java

Android SDK

JSON Parsing (org.json)

ListView + CustomAdapter

Drawable Resource Management

Intent Data Transfer

UI Formatting Utilities
