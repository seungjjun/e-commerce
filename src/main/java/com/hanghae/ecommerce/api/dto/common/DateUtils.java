package com.hanghae.ecommerce.api.dto.common;

import java.time.format.DateTimeFormatter;

public class DateUtils {
	public static final DateTimeFormatter DATE_TIME_FORMATTER =
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
