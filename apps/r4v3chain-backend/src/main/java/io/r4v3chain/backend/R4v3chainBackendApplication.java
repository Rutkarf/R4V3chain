package io.r4v3chain.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableScheduling
public class R4v3chainBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(R4v3chainBackendApplication.class, args);
	}

}
