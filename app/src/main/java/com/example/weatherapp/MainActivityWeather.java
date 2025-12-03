package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityWeather extends AppCompatActivity {

    ArrayList<Weather> weatherData;
    String zipData;
    
    TextView textViewlat, textViewlong, textViewCity, textViewtemp, textViewhigh, textViewlow,
            textViewfeels, textViewdesc, textViewdate, textViewhumidity, textViewwind;
    ImageView imageView;
    ListView listView;
    
    
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);


        zipData = getIntent().getStringExtra("Zip Code");

        textViewlat = findViewById(R.id.textViewLat);
        textViewlong = findViewById(R.id.textViewLong);
        textViewdate = findViewById(R.id.textViewDate);
        textViewCity = findViewById(R.id.textViewCity);
        textViewtemp = findViewById(R.id.textViewTemp);
        textViewhigh = findViewById(R.id.textViewHigh);
        textViewlow = findViewById(R.id.textViewLow);
        textViewfeels = findViewById(R.id.textViewFeels);
        textViewdesc = findViewById(R.id.textViewWeather);
        textViewhumidity = findViewById(R.id.textViewHumidity);
        textViewwind = findViewById(R.id.textViewWind);
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.listView);
        weatherData = new ArrayList<>();


        try {
            JSONObject object = new JSONObject(zipData);
            JSONArray jsonArray = object.getJSONArray("list");
            //JSONArray jsonArray = new JSONArray(zipData);

            setTime(textViewdate, object.getJSONArray("list").getJSONObject(0).getString("dt_txt"));
            textViewCity.setText(object.getJSONObject("city").getString("name"));
            textViewlat.setText("Lat: " + object.getJSONObject("city").getJSONObject("coord").getDouble("lat"));
            textViewlong.setText("Long: " + object.getJSONObject("city").getJSONObject("coord").getDouble("lon"));
            textViewtemp.setText(round(object.getJSONArray("list").getJSONObject(0).getJSONObject(
                    "main").getDouble("temp")) + "°");
            textViewhigh.setText("High: " + object.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_max") + "°");
            textViewlow.setText("Low: " + object.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min") + "°");
            textViewfeels.setText("Feels Like " + object.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("feels_like") + "°");
            textViewwind.setText(object.getJSONArray("list").getJSONObject(0).getJSONObject("wind").getDouble("speed") + " mph");
            textViewhumidity.setText(object.getJSONArray("list").getJSONObject(0).getJSONObject("main").getInt("humidity") + "%");
            textViewdesc.setText(capitalize(object.getJSONArray("list").getJSONObject(0).getJSONArray(
                    "weather").getJSONObject(0).getString("description")));
            String picturedesc = object.getJSONArray("list").getJSONObject(0).getJSONArray(
                    "weather").getJSONObject(0).getString("icon");
            imageView.setImageResource(choosePicture(picturedesc));
            Log.d("MainActivityWeather", "JSON: " + jsonArray.length());
            for (int i = 1; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String date = jsonObject.getString("dt_txt");
                double temp = jsonObject.getJSONObject("main").getDouble("temp");
                double low = jsonObject.getJSONObject("main").getDouble("temp_min");
                double high = jsonObject.getJSONObject("main").getDouble("temp_max");
                double wind = jsonObject.getJSONObject("wind").getDouble("speed");
                double feels = jsonObject.getJSONObject("main").getDouble("feels_like");
                int humidity = jsonObject.getJSONObject("main").getInt("humidity");
                String description = capitalize(jsonObject.getJSONArray("weather").getJSONObject(0).getString("description"));
                String descriptionImage = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");

                weatherData.add(new Weather(round(temp), high, low, feels, wind, humidity,
                        description, choosePicture(descriptionImage), setDate(date)));

                //Log.d("Tag", "new Weather: " + weatherData.get(i-1).toString());
            }

        } catch (JSONException e) {
            Log.e("MainActivityWeather", "Error parsing JSON", e);
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.adapter_layout, weatherData);
        listView.setAdapter(adapter);

    }

    private int choosePicture(String icon) {
        switch (icon) {
            case "01d":
                return R.drawable.sunny;
            case "01n":
                return R.drawable.night;
            case "02d":
                return R.drawable.cloudyday;
            case "02n":
                return R.drawable.cloudynight;
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                return R.drawable.scatteredclouds;
            case "09d":
            case "09n":
            case "10d":
            case "10n":
                return R.drawable.cloudyrain;
            case "11d":
            case "11n":
                return R.drawable.thunderstorm;
            case "13d":
            case "13n":
                return R.drawable.snow;
            case "50d":
            case "50n":
                return R.drawable.rainy;
        }
        return 0;
    }
    private String capitalize(String s) {
        if(s.contains(" "))
            return s.substring(0, 1).toUpperCase() + s.substring(1, s.indexOf(" ")) + " " + s.substring(s.indexOf(" ") + 1, s.indexOf(" ") + 2).toUpperCase() + s.substring(s.indexOf(" ") + 2);
        else
            return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    private String setDate(String date) {
        String[] split = date.split(" ");
        String[] timeA = split[1].split(":");
        String time;
        int hour = Integer.parseInt(timeA[0]);
        if(hour == 0) {
            time = "12 AM";
        } else if(hour == 12) {
            time = "12 PM";
        } else if(hour > 12) {
            time = Integer.toString(hour - 12) + " PM";
        } else {
            time = Integer.toString(hour) + " AM";
        }

        String[] dateSplit = split[0].split("-");
        if(dateSplit[2].charAt(0) == '0')
            dateSplit[2] = dateSplit[2].substring(1);

        switch (dateSplit[1]) {
            case "01":
                return time + " Jan " + dateSplit[2];
            case "02":
                return time + " Feb " + dateSplit[2];
            case "03":
                return time + " Mar " + dateSplit[2];
            case "04":
                return time + " Apr " + dateSplit[2];
            case "05":
                return time + " May " + dateSplit[2];
            case "06":
                return time + " Jun " + dateSplit[2];
            case "07":
                return time + " Jul " + dateSplit[2];
            case "08":
                return time + " Aug " + dateSplit[2];
            case "09":
                return time + " Sep " + dateSplit[2];
            case "10":
                return time + " Oct " + dateSplit[2];
            case "11":
                return time + " Nov " + dateSplit[2];
            case "12":
                return time + " Dec " + dateSplit[2];
        }
        return date;
    }
    @SuppressLint("SetTextI18n")
    public void setTime(TextView textView, String date) {
        String[] split = date.split(" ");
        String[] timeA = split[1].split(":");
        String time = "";
        int hour = Integer.parseInt(timeA[0]);
        if(hour == 0) {
            time = "12 AM";
        } else if(hour == 12) {
            time = "12 PM";
        } else if(hour > 12) {
            time = Integer.toString(hour - 12) + " PM";
        } else {
            time = Integer.toString(hour) + " AM";
        }

        String[] dateSplit = split[0].split("-");
        if(dateSplit[2].charAt(0) == '0')
            dateSplit[2] = dateSplit[2].substring(1);

        switch (dateSplit[1]) {
            case "01":
                textView.setText(time + " | Jan " + dateSplit[2]);
                break;
            case "02":
                textView.setText(time + " | Feb " + dateSplit[2]);
                break;
            case "03":
                textView.setText(time + " | Mar " + dateSplit[2]);
                break;
            case "04":
                textView.setText(time + " | Apr " + dateSplit[2]);
                break;
            case "05":
                textView.setText(time + " | May " + dateSplit[2]);
                break;
            case "06":
                textView.setText(time + " | Jun " + dateSplit[2]);
                break;
            case "07":
                textView.setText(time + " | Jul " + dateSplit[2]);
                break;
            case "08":
                textView.setText(time + " | Aug " + dateSplit[2]);
                break;
            case "09":
                textView.setText(time + " | Sep " + dateSplit[2]);
                break;
            case "10":
                textView.setText(time + " | Oct " + dateSplit[2]);
                break;
            case "11":
                textView.setText(time + " | Nov " + dateSplit[2]);
                break;
            case "12":
                textView.setText(time + " | Dec " + dateSplit[2]);
                break;
        }

    }
    private int round(double d) {
        return (int) Math.round(d);
    }

}