package com.hanghae.ecommerce.storage.user;

import com.hanghae.ecommerce.domain.user.User;
import com.hanghae.ecommerce.storage.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "point")
    private Long point;

    public User toUser() {
        return new User(getId(), name, address, phoneNumber, point);
    }

    public void updatePoint(User user) {
        this.point = user.point();
    }
}
