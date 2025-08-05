package com.example.lusilab.SmartHospital.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Ky shërbim duhet të jetë në gjendje të ngarkojë të dhëna për të gjitha rolet
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Fikja e CSRF (për testim)
                .authorizeHttpRequests(auth -> auth
                        // Lejo aksesin publik në faqet kryesore, zgjedhjen e rolit, të gjitha faqet e login/signup, dhe resurset statike
                        .requestMatchers(
                                "/", "/home", "/choose-role",
                                "/admin/login", "/admin/signup",
                                "/doctor/login", "/doctor/signup",
                                "/nurse/login", "/nurse/signup",
                                "/patient/login", "/patient/signup",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()
                        // Cakto rregullat e autorizimit për çdo rol
                        .requestMatchers("/admin/**").hasAuthority("Admin")
                        .requestMatchers("/doctor/**").hasAuthority("Doctor")
                        .requestMatchers("/nurse/**").hasAuthority("Nurse")
                        .requestMatchers("/patient/**").hasAuthority("Patient")
                        // Çdo kërkesë tjetër që nuk përputhet me rregullat e mësipërme duhet të jetë e autentikuar
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // Nëse kërkohet login, ridrejto te kjo faqe
                        .loginPage("/choose-role")
                        // URL-ja ku të gjitha format e login-it do të bëjnë POST
                        .loginProcessingUrl("/login")
                        // TË GJITHA rolet do të ridrejtohen këtu pas login-it të suksesshëm
                        .defaultSuccessUrl("/dashboard", true)
                        // Nëse login dështon, kthehu te faqja e zgjedhjes së rolit me një parametër gabimi
                        .failureUrl("/choose-role?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        // URL-ja për të kryer logout
                        .logoutUrl("/logout")
                        // Faqja ku do të shkosh pas logout-it
                        .logoutSuccessUrl("/choose-role?logout=true")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}