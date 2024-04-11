package com.hanghae.ecommerce.domain.user;

import org.springframework.stereotype.Service;

@Service
public class UserPointService {
    private final UserReader userReader;
    private final UserPointManager userPointManager;

    public UserPointService(UserReader userReader, UserPointManager userPointManager) {
        this.userReader = userReader;
        this.userPointManager = userPointManager;
    }

    public Long chargePoint(Long userId, Long amount) {
        User user = userReader.readById(userId);
        User chargedUser = userPointManager.chargePoint(user, amount);
        return chargedUser.point();
    }

    public Long getPoint(Long userId) {
        User user = userReader.readById(userId);
        return user.point();
    }
}
