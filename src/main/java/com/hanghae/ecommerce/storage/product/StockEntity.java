package com.hanghae.ecommerce.storage.product;

import com.hanghae.ecommerce.domain.product.Stock;
import com.hanghae.ecommerce.storage.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_stocks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockEntity extends BaseEntity {
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "stock_quantity")
    private Long stockQuantity;

    public Stock toStock() {
        return new Stock(getId(), productId, stockQuantity);
    }

    public void updateStock(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
