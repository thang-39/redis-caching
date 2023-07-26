package com.thang.redisspring.city.client;

import com.thang.redisspring.city.dto.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityClient {

    private WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url) {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Mono<City> getCity(final String zipCode) {
        return webClient
                .get()
                .uri("/{zipcode}", zipCode)
                .retrieve()
                .bodyToMono(City.class);
    }

    public Flux<City> getAllCities() {
        return webClient
                .get()
                .retrieve()
                .bodyToFlux(City.class);
    }
}
