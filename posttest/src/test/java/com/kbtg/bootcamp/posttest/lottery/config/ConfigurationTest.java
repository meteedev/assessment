package com.kbtg.bootcamp.posttest.lottery.config;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class ConfigurationTest {

	@Test
	public void test_Active_Profile_Deploy_Must_PRODUCTION() throws IOException {
		// Load the application.properties file
		ClassPathResource resource = new ClassPathResource("application.properties");
		Properties properties = PropertiesLoaderUtils.loadProperties(resource);

		// Get the value of spring.profiles.active property
		String activeProfile = properties.getProperty("spring.profiles.active");

		// Assert that the spring.profiles.active property has the expected value
		assertEquals("prod", activeProfile);
	}


}
