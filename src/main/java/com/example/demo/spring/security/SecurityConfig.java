package com.example.demo.spring.security;

import com.example.demo.spring.config.AppSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// Minimal security setup: docs and student endpoints are public, course endpoints require auth.
public class SecurityConfig {

	private final AppSecurityProperties securityProperties;

	public SecurityConfig(AppSecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					"/swagger-ui.html",
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/actuator/health",
					"/actuator/health/**",
					"/h2-console/**",
					"/api/learning-info",
					"/api/students/**"
				).permitAll()
				.requestMatchers("/api/courses/**").authenticated()
				.anyRequest().permitAll()
			)
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		// In-memory users keep the example small and avoid a security database setup.
		UserDetails user = User.withUsername(securityProperties.getUsername())
			.password(passwordEncoder.encode(securityProperties.getPassword()))
			.roles(securityProperties.getRole())
			.build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
