package com.example.biskit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthEntryPoint jwtAuthEntryPoint;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /*
     * Se recomienda desactivar CSRF cuando se la comunicación se están manejando
     * páginas web
     * donde la comunicación entre la página y el servidor es mediante peticiones
     * HTTP
     */
    http
      .csrf(AbstractHttpConfigurer::disable)
      .headers(headers -> headers.frameOptions(frame -> frame.disable()))
      .sessionManagement(customizer ->
        customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers("/h2/**").permitAll()
          .requestMatchers("/login/**").permitAll()
          .requestMatchers("/enfermedades/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/razas/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/especies/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/especialidades/**").hasAuthority("ADMIN")
          .requestMatchers("/vets/details").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/vets/{id}/tratamientos/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/vets/{id}/horario-semanal").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/vets/{id}/citas-semanales").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/vets/**").hasAuthority("ADMIN")
          .requestMatchers("/admin/**").hasAuthority("ADMIN")
          .requestMatchers("/citas/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/clients/{id}/**").hasAnyAuthority("ADMIN", "VET", "CLIENT")
          .requestMatchers("/clients/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/drogas/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/filtros/**").hasAnyAuthority("ADMIN", "VET")
          .requestMatchers("/pets/{id}").hasAnyAuthority("ADMIN", "VET", "CLIENT")
          .requestMatchers("/pets/{id}/tratamientos").hasAnyAuthority("CLIENT")
          .requestMatchers("/pets/**").hasAnyAuthority("ADMIN", "VET")
          .anyRequest()
          .permitAll()
      )
      .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * Permite autenticar a los usuarios con usuario y contrasena
   * Al autenticar devuelve un onjeto Authentication que posteriormente se puede usar a traves de SecurityContextHolder
   * para obtener el usuario autenticado
   */
  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }
}
