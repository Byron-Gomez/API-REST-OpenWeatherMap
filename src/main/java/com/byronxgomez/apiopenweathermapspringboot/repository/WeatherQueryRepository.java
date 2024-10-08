package com.byronxgomez.apiopenweathermapspringboot.repository;


import com.byronxgomez.apiopenweathermapspringboot.model.entity.WeatherQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherQueryRepository extends JpaRepository<WeatherQuery, Long> {

    // Buscar consultas por ciudad
    List<WeatherQuery> findByCity(String city);

    // Buscar consultas por ciudad ignorando mayúsculas y minúsculas
    List<WeatherQuery> findByCityIgnoreCase(String city);

    // Buscar la última consulta por ciudad
    Optional<WeatherQuery> findTopByCityOrderByQueryDateDesc(String city);

    // Buscar todas las consultas hechas en una fecha específica
   // List<WeatherQuery> findAllByQueryDate(String queryDate);
}