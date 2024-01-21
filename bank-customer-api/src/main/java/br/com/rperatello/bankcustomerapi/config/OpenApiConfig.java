package br.com.rperatello.bankcustomerapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
		
		@Bean
		public OpenAPI customOpenAPI() {
			return new OpenAPI()
				.info(new Info()
					.title("Bank - Customer API")
					.version("v1")
					.description("Bank - Customer API")
					.termsOfService("https://www.rperatello.com.br")
					.license(
						new License()
							.name("Apache 2.0")
							.url("https://www.rperatello.com.br")
						)
					);
		}

}
