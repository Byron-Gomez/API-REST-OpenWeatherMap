package com.byronxgomez.apiopenweathermapspringboot;

import com.byronxgomez.apiopenweathermapspringboot.controller.WeatherController;
import com.byronxgomez.apiopenweathermapspringboot.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class WeatherControllerTest {

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para el método getCurrentWeather
    @Test
    void testGetCurrentWeather() {
        String city = "London";
        Map<String, Object> expectedResponse = new HashMap<>(); // Respuesta esperada
        expectedResponse.put("temperature", 20); // Datos de ejemplo para la temperatura
        expectedResponse.put("description", "Sunny"); // Datos de ejemplo para la descripción

        // Simula la respuesta del servicio WeatherService
        when(weatherService.getCurrentWeather(city)).thenReturn(expectedResponse);

        // Llama al método del controlador
        Map<String, Object> actualResponse = weatherController.getCurrentWeather(city);

        // Verifica que la respuesta obtenida sea igual a la esperada
        assertEquals(expectedResponse, actualResponse);
    }

    // Test para el método get5DayForecast
    @Test
    void testGet5DayForecast() {
        String city = "London";
        Map<String, Object> expectedResponse = new HashMap<>(); // Respuesta esperada
        expectedResponse.put("forecast", "5 days of weather data"); // Datos de ejemplo para el pronóstico

        // Simula la respuesta del servicio WeatherService
        when(weatherService.get5DayForecast(city)).thenReturn(expectedResponse);

        // Llama al método del controlador
        Map<String, Object> actualResponse = weatherController.get5DayForecast(city);

        // Verifica que la respuesta obtenida sea igual a la esperada
        assertEquals(expectedResponse, actualResponse);
    }

    // Test para el método getAirQuality
    @Test
    void testGetAirQuality() {
        String city = "London"; // Ciudad de ejemplo
        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("airQuality", "Good"); // Datos de ejemplo para la calidad del aire

        // Simula la respuesta del servicio WeatherService
        when(weatherService.getAirQuality(city)).thenReturn(expectedResponse);

        // Llama al método del controlador
        Map<String, Object> actualResponse = weatherController.getAirQuality(city);

        // Verifica que la respuesta obtenida sea igual a la esperada
        assertEquals(expectedResponse, actualResponse);
    }
}
