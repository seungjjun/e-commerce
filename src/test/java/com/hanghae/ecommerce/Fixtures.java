package com.hanghae.ecommerce;

import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.domain.user.User;
import jakarta.persistence.EntityNotFoundException;

public class Fixtures {
    public static Product product(String name) {
        if (name.equals("후드티")) {
            return new Product(1L, "후드티", 50_000L, "그레이 후드티", 5L);
        }

        if (name.equals("맨투맨")) {
            return new Product(2L, "맨투맨", 39_000L, "늘어나지 않는 맨투맨", 10L);
        }

        throw new EntityNotFoundException("Product Not Found - name: " + name);
    }

    public static User user(Long id) {
        if (id.equals(1L)) {
            return new User(1L, "홍길동", "서울특별시 송파구", "01012345678", 50_000L);
        }

        throw new EntityNotFoundException("User Not Found - id: " + id);
    }

}
