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
            .csrf((csrf -> csrf
                .disable()))
            .authorizeHttpRequests((auth) -> auth
                .requestMatchers("/oauth/**","/prijava", "/registracija", "/registracija-1", "/registracija-2", "/registracija-3", "/potvrdi",
                "/", "/kontakt", "/oglasi", "/pretraga", "/zaboravljena-lozinka", "/reset-lozinke", "/pravila-privatnosti", "/uvjeti-koristenja", "/manufacturers", "/manufacturersSearch", "/models", "/modelsSearch",
                "/css/**", "/img/**", "/js/**", "/oglasi/**", "/backRegistration", "/error-404", "/error-500", "/korisnicki-profil/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((formLogin) -> formLogin
                .loginPage("/prijava")
                .loginProcessingUrl("/prijava")
                .defaultSuccessUrl("/?uspjesnaPrijava", true)
                .failureUrl("/prijava?greska")
                // .successHandler(databaseLoginSuccessHandler)
                .permitAll()
                
            )
            .oauth2Login((formLogin)-> formLogin
                .loginPage("/prijava")
                .userInfoEndpoint( (userInfoEndpoint -> userInfoEndpoint
                    .userService(oauth2UserService)))
                .successHandler(oauthLoginSuccessHandler)
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

    @Autowired
    private CustomOAuth2UserService oauth2UserService;
     
    @Autowired
    private OAuthLoginSuccessHandler oauthLoginSuccessHandler;
     
    // @Autowired
    // private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;
}