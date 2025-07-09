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
        OpenAPI openApi = new OpenAPI();

        openApi.info(new Info()
                        .title("CleanView Backend API")
                        .description("CleanView backend REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("CleanView Backend Documentation")
                        .url("https://github.com/"));

        final String securitySchemeName = "Bearer Authentication";
        openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));

        // ✅ Agrega servers manualmente SOLO en producción
        if (isProduction()) {
            openApi.setServers(List.of(
                    new Server()
                            .url("https://backend-web-applications-production-cb75.up.railway.app")
                            .url("https://backend-web-applications-nhtl.onrender.com")
                            .description("Railway Deployment")
            ));
        }

        return openApi;
    }

    // 🧠 Método auxiliar
    private boolean isProduction() {
        return "production".equals(System.getenv("SPRING_ENV"));
    }

}
