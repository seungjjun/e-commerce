package com.hanghae.ecommerce.domain.product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
}
