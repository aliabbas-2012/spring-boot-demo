package com.dev.demo;

import com.dev.demo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testExistsByEmailAndIdNot() {
		boolean exists = userRepository.existsByEmailAndIdNot("test@example.com", 1L);
		System.out.println("Exists:....... " +  exists);
	}

}
