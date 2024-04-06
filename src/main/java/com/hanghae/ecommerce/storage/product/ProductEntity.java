package com.hanghae.ecommerce.storage.product;

import com.hanghae.ecommerce.domain.product.Product;
import com.hanghae.ecommerce.storage.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "stock_quantity")
    private Long stockQuantity;

    public Product toProduct() {
        return new Product(getId(), name, price, description, stockQuantity);
    }
}
