package com.mclabs.securities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class SecuritiesApplicationTests {

	@Autowired
	private PasswordEncoder encoder;
	
	// the log out can use for make a entry into DB as encoded password
	@Test
	void contextLoads() {
		System.out.println(encoder.encode("jithin"));
	}

}
