package com.blog.cesmusic;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		info =
		@Info(
				title = "API Cesmusic",
				version = "v1",
				description = "API REST utilizada para um projeto de blog musical denominado Cesmusic",
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		)
)
public class CesmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(CesmusicApplication.class, args);
	}

}
