package com.kimgreen.backend.config.Swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
        info = @Info(title = "김그린 API 명세서",
                description = "김그린 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {


        @Bean
        public OpenAPI openAPI(){
            SecurityScheme AccessToken = new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER).name("Authorization");
            SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");
/*
            SecurityScheme RefreshToken  = new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER);
*/
            return new OpenAPI()
                    .components(new Components().addSecuritySchemes("bearerAuth", AccessToken))
                    .security(Arrays.asList(securityRequirement));
        }

}
