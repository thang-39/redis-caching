package com.thang.redisspring.city.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CityClient {

    private WebClient webClient;

    public CityClient(@Value("${city.service.url}") String url) {
        we
    }
}
