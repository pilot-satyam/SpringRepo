package com.ImyEye.info.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ImyEye.info.Security.CustomUserDetailService;
import com.ImyEye.info.Security.JwtAuthenticationEntryPoint;
import com.ImyEye.info.Security.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)

public class SecurityConfig{
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
/**
 * @param http
 * @return
 * @throws Exception
 */
@Bean
public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
    http
    .csrf()
    .disable()
    .authorizeHttpRequests()
    // .requestMatchers("/api/v1/auth/login")
    // .requestMatchers("/api/v1/auth/**")
    // .permitAll()
    // .requestMatchers("/v3/api-docs")
    // .permitAll()
    // .requestMatchers(HttpMethod.GET)
    // .permitAll()
    // .anyRequest()
    // .authenticated()
    
    .requestMatchers("/api/v1/auth/login").authenticated()
    .anyRequest().permitAll()
    .and()
    .exceptionHandling()
    .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
    .and()
    .sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.authenticationProvider(daoAuthenticationProvider());
    DefaultSecurityFilterChain defaultSecurityChain = http.build();
    return defaultSecurityChain;
}

@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

@Bean
public DaoAuthenticationProvider daoAuthenticationProvider(){
DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
provider.setUserDetailsService(this.customUserDetailService);
provider.setPasswordEncoder(passwordEncoder());
return provider;
}

@Bean
public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception
{
    return configuration.getAuthenticationManager();
}

}
