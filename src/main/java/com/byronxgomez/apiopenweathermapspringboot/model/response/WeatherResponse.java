package com.byronxgomez.apiopenweathermapspringboot.model.response;


public class WeatherResponse {
    private Main main;
    private Wind wind;
    private String name;

    // Getters y setters

    public static class Main {
        private double temp;
        private int humidity;

        // Getters y setters
    }

    public static class Wind {
        private double speed;

        // Getters y setters
    }
}
