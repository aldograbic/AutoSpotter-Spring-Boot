package com.example.AutoSpotter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig {

    // private JwtAuthEntryPoint authEntryPoint;
    // private CustomUserDetailsService userDetailsService;

    // @Autowired
    // public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
    //     this.userDetailsService = userDetailsService;
    //     this.authEntryPoint = authEntryPoint;
    // }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/prijava", "/registracija", "/zaboravljena-lozinka", "/", "/pretraga", "/oglasi", "/oglasi/{listingId}", "/kontakt", "/img/**", "/css/**", "/js/**").permitAll()
            .and()
            .formLogin(form -> form
                                .loginPage("/prijava")
                                .defaultSuccessUrl("/")
                                .loginProcessingUrl("/prijava")
                                .failureUrl("/prijava")
                                .permitAll()
            )
            .logout(logout -> logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/odjava")).permitAll()

            );
        return http.build();
        //         .csrf().disable()
        //         .exceptionHandling()
        //         .authenticationEntryPoint(authEntryPoint)
        //         .and()
        //         .sessionManagement()
        //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //         .and()
        //         .authorizeHttpRequests()
        //         .requestMatchers("/**").permitAll() // ("api/auth/**/") bi trebalo ici
        //         .anyRequest().authenticated()
        //         .and()
        //         .httpBasic();
        // http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        // return http.build();
    }

    
    // @Bean
    // public JwtAuthenticationFilter jwtAuthenticationFilter() {
    //     return new JwtAuthenticationFilter();
    // }
}
