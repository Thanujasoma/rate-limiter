package com.twotheta.ratelimiter.config;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rateLimiterOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Rate Limiter API")
                        .description("Take Home Assignment - TwoTheta")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Thanuja Soma")
                                .email("thanujasoma2004@gmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository"));
    }

}
