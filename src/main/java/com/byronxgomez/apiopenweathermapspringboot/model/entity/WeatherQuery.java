package com.byronxgomez.apiopenweathermapspringboot.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weather_queries")
public class WeatherQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @Column(length = 2000000)  // Para guardar JSON o una respuesta larga
    private String weatherResponse;

    private LocalDateTime queryDate;

    public WeatherQuery(String city, String weatherResponse) {
        this.city = city;
        this.weatherResponse = weatherResponse;
        this.queryDate = LocalDateTime.now();
    }



}
