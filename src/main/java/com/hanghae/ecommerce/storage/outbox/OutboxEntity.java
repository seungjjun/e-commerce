package com.hanghae.ecommerce.storage.outbox;

import com.hanghae.ecommerce.storage.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "outbox")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEntity extends BaseEntity {

	private Long aggregateId;

	@Enumerated(EnumType.STRING)
	private OutboxType type;

	@Enumerated(EnumType.STRING)
	private OutboxStatus status;

	private String topic;

	@Lob
	@Column(columnDefinition = "LONGTEXT")
	private String payload;

	public OutboxEntity(Long aggregateId, OutboxType type, OutboxStatus status, String topic, String payload) {
		this.aggregateId = aggregateId;
		this.type = type;
		this.status = status;
		this.topic = topic;
		this.payload = payload;
	}

	public void changeStatus(OutboxStatus status) {
		this.status = status;
	}
}
