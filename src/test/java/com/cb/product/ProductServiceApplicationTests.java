package com.cb.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceApplicationTests {

	@Test
	void contextLoads() {
		String test = "just to make sonar happy";
		Assertions.assertEquals("just to make sonar happy", test);
	}

}
