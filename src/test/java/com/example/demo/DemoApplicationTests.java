package com.example.demo;

import com.example.demo.spring.persistence.store.CourseStore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
// Small smoke test that proves the Spring application context can start.
class DemoApplicationTests {

	@MockitoBean
	private CourseStore courseStore;

	@Test
	void contextLoads() {
	}

}
