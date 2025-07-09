package pe.upc.cleanview.backend.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI cleanviewBackendOpenApi() {
        var openApi = new OpenAPI();

        openApi
                .info(new Info()
                        .title("CleanView Backend API")
                        .description("CleanView backend REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("CleanView Backend Documentation")
                        .url("https://github.com/"));

        // 🔐 Seguridad (JWT)
        final String securitySchemeName = "Bearer Authentication";
        openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));

        // 🌐 Servidores (añadido manualmente)
        openApi.setServers(List.of(
                new Server()
                        .url("https://backend-web-applications-production-cb75.up.railway.app")
                        .description("Producción HTTPS"),
                new Server()
                        .url("http://localhost:8080")
                        .description("Desarrollo Local")
        ));

        return openApi;
    }
}
