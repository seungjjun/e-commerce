package com.hanghae.ecommerce.domain.user;

import com.hanghae.ecommerce.api.error.InsufficientPointException;

public record User(
	Long id,
	String name,
	String address,
	String phoneNumber,
	Long point
) {
	public User addPoint(Long chargingPoint) {
		return new User(id, name, address, phoneNumber, point + chargingPoint);
	}

	public User minusPoint(Long amount) {
		return new User(id, name, address, phoneNumber, point - amount);
	}

	public void isEnoughPointForPay(Long payAmount) {
		if (point < payAmount) {
			throw new InsufficientPointException("현재 잔액이 " + point + " 으로 잔액이 충분하지 않습니다.");
		}
	}
}
