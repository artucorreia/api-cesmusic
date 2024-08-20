package com.blog.cesmusic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = CesmusicApplication.class)
@ActiveProfiles("test")
class CesmusicApplicationTests {

	@Test
	void contextLoads() {
	}

}
