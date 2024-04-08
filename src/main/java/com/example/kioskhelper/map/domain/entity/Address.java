package com.example.kioskhelper.map.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Address {

    private double latitude;
    private double longitude;

    @Builder
    public Address(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
