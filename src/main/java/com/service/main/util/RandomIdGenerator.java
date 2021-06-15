package com.service.main.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomIdGenerator {

	public String randomId( ) {
		String generatedString = RandomStringUtils.randomAlphanumeric(6);
		return generatedString;
	}
	
	public String customId(int limit) {
		String generatedString = RandomStringUtils.randomAlphanumeric(limit);
		return generatedString;
	}
}
