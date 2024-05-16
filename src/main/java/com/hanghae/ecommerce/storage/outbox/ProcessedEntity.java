package com.hanghae.ecommerce.storage.outbox;

import com.hanghae.ecommerce.storage.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "processed")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProcessedEntity extends BaseEntity {
	private Long orderId;

	public ProcessedEntity(Long orderId) {
		this.orderId = orderId;
	}
}
