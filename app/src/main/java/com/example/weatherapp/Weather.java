package com.example.weatherapp;

public class Weather {

    private int temperature;
    private double hightemp;
    private double lowtemp;
    private double feelslike;
    private double wind;
    private int humidity;
    private String description;
    private int descriptopnImage;
    private String date;


    public Weather(int temperature, double hightemp, double lowtemp, double feelslike, double wind,
                   int humidity, String description, int descriptopnImage, String date) {
        this.temperature = temperature;
        this.hightemp = hightemp;
        this.lowtemp = lowtemp;
        this.feelslike = feelslike;
        this.wind = wind;
        this.humidity = humidity;
        this.description = description;
        this.descriptopnImage = descriptopnImage;
        this.date = date;
    }


    public int getTemperature() {
        return temperature;
    }
    public double getHightemp() {
        return hightemp;
    }
    public double getLowtemp() {
        return lowtemp;
    }
    public double getFeelslike() {
        return feelslike;
    }
    public double getWind() {
        return wind;
    }
    public int getHumidity() {
        return humidity;
    }
    public String getDescription() {
        return description;
    }
    public int getDescriptopnImage() {
        return descriptopnImage;
    }
    public String getDate() {
        return date;
    }
    public String toString() {
        return "Temperature: " + temperature + " High: " + hightemp + " Low: " + lowtemp + " Feels Like: " + feelslike + " Wind: " + wind + " Humidity: " + humidity + " Description: " + description + " Description Image: " + descriptopnImage + " Date: " + date;
    }
}
