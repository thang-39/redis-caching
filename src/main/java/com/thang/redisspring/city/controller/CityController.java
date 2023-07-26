package com.thang.redisspring.city.controller;

import com.thang.redisspring.city.dto.City;
import com.thang.redisspring.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/{zipcode}")
    public Mono<City> getCity(@PathVariable String zipcode) {
        return cityService.getCityAssignment(zipcode);
    }
}
