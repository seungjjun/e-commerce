package com.hanghae.ecommerce.domain.user;

public interface UserRepository {

    User findById(Long userId);

    User updateUserPoint(User user);
}
