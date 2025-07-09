package pe.upc.cleanview.backend.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import pe.upc.cleanview.backend.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import pe.upc.cleanview.backend.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import pe.upc.cleanview.backend.iam.infrastructure.security.CustomUserDetailsService;
import pe.upc.cleanview.backend.iam.infrastructure.tokens.jwt.BearerTokenService;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

  private final CustomUserDetailsService customUserDetailsService;
  private final BearerTokenService tokenService;
  private final BCryptHashingService hashingService;
  private final AuthenticationEntryPoint unauthorizedRequestHandler;

  public WebSecurityConfiguration(
          CustomUserDetailsService customUserDetailsService,
          BearerTokenService tokenService,
          BCryptHashingService hashingService,
          AuthenticationEntryPoint authenticationEntryPoint) {
    this.customUserDetailsService = customUserDetailsService;
    this.tokenService = tokenService;
    this.hashingService = hashingService;
    this.unauthorizedRequestHandler = authenticationEntryPoint;
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
  public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder); // ✅ aseguramos inyección
    return provider;
  }




  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  // 🔥 Filtro CORS global con alta prioridad (para Railway y Swagger local)
  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:8080", "https://backend-web-applications-production-cb75.up.railway.app"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // ⚠️ Muy importante
    return bean;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider authenticationProvider) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedRequestHandler))
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/v1/authentication/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**",
                            "/swagger-resources/**",
                            "/webjars/**",
                            "/api/v1/profiles/**",
                            "/api/v1/publications/**",
                            "/api/v1/bonds/all-bonds",
                            "/api/v1/sustainable-actions",
                            "/api/v1/sustainable-actions/**"
                    ).permitAll()
                    .anyRequest().authenticated())
            .authenticationProvider(authenticationProvider) // ✅ ya viene como parámetro
            .addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
