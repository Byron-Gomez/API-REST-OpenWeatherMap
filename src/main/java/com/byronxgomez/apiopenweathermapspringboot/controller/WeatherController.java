package com.byronxgomez.apiopenweathermapspringboot.controller;

import com.byronxgomez.apiopenweathermapspringboot.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/weather")
@Api(tags = "Weather API", description = "Operaciones relacionadas con el clima")
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * Constructor que inicializa el servicio de clima.
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Endpoint para obtener el clima actual de una ciudad especificada.
     *
     * @param city el nombre de la ciudad para la cual se desea obtener el clima actual
     * @return un mapa que contiene información sobre el clima actual
     */
    @ApiOperation(value = "Obtener el clima actual", notes = "Este endpoint devuelve el clima actual de la ciudad especificada") // Documentación de la operación
    @GetMapping("/current")
    public Map<String, Object> getCurrentWeather(@RequestParam String city) {
        return weatherService.getCurrentWeather(city);
    }

    /**
     * Endpoint para obtener el pronóstico del clima para los próximos 5 días.
     *
     * @param city el nombre de la ciudad para la cual se desea obtener el pronóstico
     * @return un mapa que contiene el pronóstico del clima
     */
    @ApiOperation(value = "Obtener pronóstico del tiempo de 5 días", notes = "Este endpoint devuelve el pronóstico del clima para la ciudad especificada") // Documentación de la operación
    @GetMapping("/forecast")
    public Map<String, Object> get5DayForecast(@RequestParam String city) {
        return weatherService.get5DayForecast(city);
    }

    /**
     * Endpoint para obtener la calidad del aire en una ciudad especificada.
     *
     * @param city el nombre de la ciudad para la cual se desea obtener la calidad del aire
     * @return un mapa que contiene información sobre la calidad del aire
     */
    @ApiOperation(value = "Obtener la calidad del aire", notes = "Este endpoint devuelve la calidad del aire para la ciudad especificada") // Documentación de la operación
    @GetMapping("/air-quality")
    public Map<String, Object> getAirQuality(@RequestParam String city) {
        return weatherService.getAirQuality(city);
    }
}
