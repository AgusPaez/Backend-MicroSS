package com.example.orders.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API PROYECTO")
                        .version("1.0")
                        .description("Documentación Swagger de orders")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .paths(new Paths()
                        .addPathItem("/orders", new io.swagger.v3.oas.models.PathItem()
                                .get(new Operation()
                                        .summary("Obtener todos las ordenes")
                                        .description("Endpoint para obtener todos los ordenes disponibles."))
                                .post(new Operation()
                                        .summary("Agregar una orden")
                                        .description("Endpoint para agregar una nueva orden.")))
                        .addPathItem("/orders/{orderId}", new io.swagger.v3.oas.models.PathItem()
                                .get(new Operation()
                                        .summary("Obtener orden por ID")
                                        .description("Endpoint para obtener una orden específica por su ID."))
                                .put(new Operation()
                                        .summary("Actualizar orden por ID")
                                        .description("Endpoint para actualizar una orden por su ID."))
                                .delete(new Operation()
                                        .summary("Eliminar orden por ID")
                                        .description("Endpoint para eliminar una orden por su ID."))));
    }
}