package com.hanghae.ecommerce.domain.user;

import org.springframework.stereotype.Component;

@Component
public class UserPointManager {
    private final UserRepository userRepository;

    public UserPointManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User chargePoint(User user, Long chargingPoint) {
        return userRepository.updateUserPoint(user.addPoint(chargingPoint));
    }
}
