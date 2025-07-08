package pe.upc.cleanview.backend.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity; // <-- ELIMINAR/COMENTAR ESTA LÍNEA
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // <-- USAR ESTA
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // <-- AÑADIR ESTA LÍNEA
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // Asegúrate de esta importación
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Asegúrate de esta importación
import org.springframework.web.filter.CorsFilter;
import pe.upc.cleanview.backend.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import pe.upc.cleanview.backend.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import pe.upc.cleanview.backend.iam.infrastructure.security.CustomUserDetailsService;
import pe.upc.cleanview.backend.iam.infrastructure.tokens.jwt.BearerTokenService;

import java.util.Arrays; // Asegúrate de esta importación
import java.util.List; // Asegúrate de esta importación


/**
 * Web Security Configuration.
 * <p>
 * This class is responsible for configuring the web security.
 * It enables the method security and configures the security filter chain.
 * It includes the authentication manager, the authentication provider,
 * the password encoder and the authentication entry point.
 * </p>
 */
@Configuration
@EnableWebSecurity // <-- AÑADIR ESTA ANOTACIÓN
@EnableMethodSecurity(prePostEnabled = true) // <-- USAR ESTA ANOTACIÓN
public class WebSecurityConfiguration {

  private final CustomUserDetailsService customUserDetailsService;
  private final BearerTokenService tokenService;
  private final BCryptHashingService hashingService;
  private final AuthenticationEntryPoint unauthorizedRequestHandler;
  private final CorsConfigurationSource corsConfigurationSource; // Ya lo tienes inyectado

  // Constructor
  public WebSecurityConfiguration(
          CustomUserDetailsService customUserDetailsService,
          BearerTokenService tokenService, BCryptHashingService hashingService,
          AuthenticationEntryPoint authenticationEntryPoint,
          CorsConfigurationSource corsConfigurationSource) { // Asegúrate de que CorsConfigurationSource se inyecta
    this.customUserDetailsService = customUserDetailsService;
    this.tokenService = tokenService;
    this.hashingService = hashingService;
    this.unauthorizedRequestHandler = authenticationEntryPoint;
    this.corsConfigurationSource = corsConfigurationSource; // Asigna aquí
  }

  @Bean
  public BearerAuthorizationRequestFilter authorizationRequestFilter() {
    return new BearerAuthorizationRequestFilter(tokenService, customUserDetailsService);
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    authenticationProvider.setPasswordEncoder(hashingService);
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return hashingService;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:59086", "http://localhost:60376", "http://localhost:59209")); // <-- Ajusta esto si tu frontend usa otros puertos
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public CorsFilter corsFilter() {

    return new CorsFilter(corsConfigurationSource());
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource())); // <-- Esto es suficiente para CORS

    http.csrf(csrfConfigurer -> csrfConfigurer.disable())
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(
                    authorizeRequests -> authorizeRequests.requestMatchers(
                                    "/api/v1/authentication/**",
                                    "/v3/api-docs/**",
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/swagger-resources/**",
                                    "/webjars/**",
                                    "/api/v1/profiles/**",
                                    "/api/v1/publications/**",
                                    "/api/v1/bonds/all-bonds",
                                    "/api/v1/sustainable-actions", // Añadido previamente
                                    "/api/v1/sustainable-actions/**" // Añadido previamente
                            )
                            .permitAll()
                            .anyRequest()
                            .authenticated());

    http.authenticationProvider(authenticationProvider());

    // ************* ORDEN DE FILTROS CRÍTICO *************
    // 1. CorsFilter debe ir primero para manejar preflight requests
    http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class); // <-- CAMBIO DE ORDEN
    // 2. Luego tu filtro JWT para autenticar
    http.addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class); // <-- Mantenemos este orden

    return http.build();
  }

  // El constructor ya está definido arriba con la inyección de CorsConfigurationSource
  // public WebSecurityConfiguration(...) { ... }
}