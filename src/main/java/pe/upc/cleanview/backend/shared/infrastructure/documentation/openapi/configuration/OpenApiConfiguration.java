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


@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI cleanviewBackendOpenApi() {
        var openApi = new OpenAPI();

        // ✅ Agrega el servidor HTTPS
        Server server = new Server();
        server.setUrl("https://backend-web-applications-production-cb75.up.railway.app"); // Asegúrate que sea https

        openApi
                .info(new Info()
                        .title("CleanView Backend API")
                        .description("CleanView backend REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("CleanView Backend Documentation")
                        .url("https://github.com/"))
                .addServersItem(server) // 👈 Aquí se agrega el server HTTPS
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));

        return openApi;
    }
}
