package com.hanghae.ecommerce.domain.user;

public record User(
        Long id,
        String name,
        String address,
        String phoneNumber,
        Long point
) {
    public User addPoint(Long chargingPoint) {
        return new User(id, name, address, phoneNumber, point + chargingPoint);
    }

    public User minusPoint(Long amount) {
        return new User(id, name, address, phoneNumber, point - amount);
    }
}
