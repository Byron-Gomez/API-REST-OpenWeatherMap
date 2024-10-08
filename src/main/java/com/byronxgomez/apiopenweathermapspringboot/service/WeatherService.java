package com.byronxgomez.apiopenweathermapspringboot.service;

import com.byronxgomez.apiopenweathermapspringboot.model.entity.WeatherQuery;
import com.byronxgomez.apiopenweathermapspringboot.repository.WeatherQueryRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {

    private final WebClient webClient;
    private final WeatherQueryRepository weatherQueryRepository;

    @Value("${openweathermap.api.key}")
    private String apiKey; // API key para acceder a OpenWeatherMap

    public WeatherService(WebClient.Builder webClientBuilder, WeatherQueryRepository weatherQueryRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5/").build();
        this.weatherQueryRepository = weatherQueryRepository;
    }

    // Obtener el clima actual y almacenar el resultado en caché
    @Cacheable(value = "weatherCache", key = "#city")
    public Map<String, Object> getCurrentWeather(String city) {
        String url = String.format("weather?q=%s&appid=%s&units=metric", city, apiKey);

        // Llamada a la API de OpenWeatherMap
        Map<String, Object> weatherData = this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Guardar la respuesta en la base de datos
        String weatherResponse = weatherData.toString();
        WeatherQuery query = new WeatherQuery(city, weatherResponse);
        weatherQueryRepository.save(query);

        return weatherData;
    }

    // Obtener pronóstico de 5 días y almacenar en caché
    @Cacheable(value = "weatherCache", key = "#city + '-forecast'")
    public Map<String, Object> get5DayForecast(String city) {
        String url = String.format("forecast?q=%s&appid=%s&units=metric", city, apiKey);

        Map<String, Object> forecastData = this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        saveWeatherQuery(city, forecastData);

        return forecastData;
    }

    // Guardar la consulta del clima en la base de datos
    private void saveWeatherQuery(String city, Map<String, Object> weatherData) {
        WeatherQuery query = new WeatherQuery(city, weatherData.toString());
        weatherQueryRepository.save(query);
    }

    // Obtener la calidad del aire según las coordenadas de la ciudad
    @Cacheable(value = "weatherCache", key = "#city + '-airquality'")
    public Map<String, Object> getAirQuality(String city) {
        String coordinatesUrl = String.format("weather?q=%s&appid=%s&units=metric", city, apiKey);
        Map<String, Object> weatherData = this.webClient.get()
                .uri(coordinatesUrl)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> coord = (Map<String, Object>) weatherData.get("coord");
        Double lat = (Double) coord.get("lat");
        Double lon = (Double) coord.get("lon");

        // Llamada a la API de calidad del aire
        String airQualityUrl = String.format("air_pollution?lat=%s&lon=%s&appid=%s", lat, lon, apiKey);
        Map<String, Object> airQualityData = this.webClient.get()
                .uri(airQualityUrl)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Guardar la respuesta en la base de datos
        String airQualityResponse = airQualityData.toString();
        WeatherQuery query = new WeatherQuery(city, airQualityResponse);
        weatherQueryRepository.save(query);

        return airQualityData;
    }
}
