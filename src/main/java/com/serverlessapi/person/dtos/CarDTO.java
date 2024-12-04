package com.serverlessapi.person.dtos;

import com.serverlessapi.person.models.CarEntity;

public record CarDTO(String brand, String model, Float maxSpeedKmH) {

    public CarDTO(CarEntity c) {
        this(c.getBrand(), c.getModel(), c.getMaxSpeedKmH());
    }

    public CarEntity toCarEntity() {
        return new CarEntity(brand, model, maxSpeedKmH);
    }
}
