package com.marlisajp.booktok_api_v2;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BooktokApiV2Application {
	public static void main(String[] args) {
		SpringApplication.run(BooktokApiV2Application.class, args);
	}
}
