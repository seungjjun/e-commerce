package com.hanghae.ecommerce.storage.product;

import com.hanghae.ecommerce.storage.order.OrderStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ProductEntity> findByIdIn(List<Long> productIds);

    @Query("SELECT p FROM ProductEntity p JOIN OrderItemEntity oi ON p.id = oi.productId " +
            "JOIN OrderEntity o ON oi.orderId = o.id " +
            "WHERE o.orderStatus = :orderStatus " +
            "AND o.orderedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY p.id " +
            "ORDER BY SUM(oi.quantity) DESC")
    Page<ProductEntity> findPopularSellingProducts(@Param("orderStatus") OrderStatus orderStatus,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate,
                                                   Pageable pageable);
}
