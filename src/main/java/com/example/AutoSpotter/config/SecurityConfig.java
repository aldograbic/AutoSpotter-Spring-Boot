package com.example.AutoSpotter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.AutoSpotter.classes.user.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/prijava", "/registracija", "/registracija-1", "/registracija-2", "/registracija-3", "/potvrdi",
                "/", "/kontakt", "/oglasi", "/pretraga", "/zaboravljena-lozinka", "/reset-lozinke", "/manufacturers", "/manufacturersSearch", "/models", "/modelsSearch",
                "/css/**", "/img/**", "/js/**", "/oglasi/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((formLogin) -> formLogin
                .loginPage("/prijava")
                .loginProcessingUrl("/prijava")
                .defaultSuccessUrl("/?uspjesnaPrijava", true)
                .failureUrl("/prijava?greska")
                .permitAll()
                
            )
            .logout((logout) -> logout
                .logoutUrl("/odjava")
                .logoutSuccessUrl("/?uspjesnaOdjava")
                .invalidateHttpSession(true)
                .permitAll())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}