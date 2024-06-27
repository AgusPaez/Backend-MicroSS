package com.example.products.swagger;

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
                        .description("Documentación Swagger de products")
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .paths(new Paths()
                        .addPathItem("/products", new io.swagger.v3.oas.models.PathItem()
                                .get(new Operation()
                                        .summary("Obtener todos los productos")
                                        .description("Endpoint para obtener todos los productos disponibles."))
                                .post(new Operation()
                                        .summary("Agregar un producto")
                                        .description("Endpoint para agregar un nuevo producto.")))
                        .addPathItem("/products/{productId}", new io.swagger.v3.oas.models.PathItem()
                                .put(new Operation()
                                        .summary("Actualizar producto por ID")
                                        .description("Endpoint para actualizar un producto por su ID."))
                                .delete(new Operation()
                                        .summary("Eliminar producto por ID")
                                        .description("Endpoint para eliminar un producto por su ID.")))
                        .addPathItem("/products/find/{id}", new io.swagger.v3.oas.models.PathItem()
                                .get(new Operation()
                                        .summary("Obtener producto por ID")
                                        .description("Endpoint para obtener un producto específico por su ID."))));
    }
}
