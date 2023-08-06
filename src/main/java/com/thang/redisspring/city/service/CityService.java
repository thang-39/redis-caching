package com.thang.redisspring.city.service;

import com.thang.redisspring.city.client.CityClient;
import com.thang.redisspring.city.dto.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityClient cityClient;

    private RMapCacheReactive<String, City> cityMap;
    private RMapReactive<String, City> cityMapAssignment;

    public CityService(RedissonReactiveClient client) {
        cityMap = client.getMapCache("city",new TypedJsonJacksonCodec(String.class, City.class));
        cityMapAssignment = client.getMap("cityAssignment",new TypedJsonJacksonCodec(String.class, City.class));
    }

    /*
        get from cache
        if empty - get from db / source
                    put it in cache
        return
    */
    public Mono<City> getCity(final String zipcode) {
        return cityMap.get(zipcode)
                .switchIfEmpty(cityClient
                                .getCity(zipcode)
                                .flatMap(c -> cityMap.fastPut(zipcode,c,10,TimeUnit.SECONDS)
                                .thenReturn(c))
                );
    }

    public Mono<City> getCityAssignment(final String zipcode) {
        return cityMapAssignment.get(zipcode)
                .onErrorResume(ex -> cityClient.getCity(zipcode));
    }

//    @Scheduled(fixedRate = 10_1000)
    public void updateCity() {
        cityClient.getAllCities()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(cityMapAssignment::putAll)
                .subscribe();


    }

}
