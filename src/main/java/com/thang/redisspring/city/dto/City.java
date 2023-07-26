package com.thang.redisspring.city.dto;

import lombok.Data;
import lombok.ToString;

/*
{
    "zip": "00602",
    "lat": 18.36074,
    "lng": -67.17519,
    "city": "Aguada",
    "stateId": "PR",
    "stateName": "Puerto Rico",
    "population": 37751,
    "density": 476,
    "temperature": 1
}
*/
@Data
@ToString
public class City {
    private String zip;
    private String city;
    private String stateName;
    private int temperature;
}
