package com.Authenticate.info;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig{

	@Autowired
	private DataSource dataSource;
	@Bean 
	public UserDetailsService userDetailService()
	{
			return new CustomUserDetailsService();
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests()
		.requestMatchers("/list_users").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.usernameParameter("email")
		.defaultSuccessUrl("/list_users")
		.permitAll()
		.and()
		.logout().logoutSuccessUrl("/").permitAll(); 
		http.authenticationProvider(authenticationProvider());
		return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
//	@Bean
//	public void configure(AuthenticationManagerBuilder auth) throws Exception
//	{
//		auth.authenticationProvider(authenticationProvider());
//	}
	
//	  @Bean
//	    AuthenticationManager ldapAuthenticationManager(
//	            BaseLdapPathContextSource contextSource) {
//	        LdapBindAuthenticationManagerFactory factory = 
//	            new LdapBindAuthenticationManagerFactory(contextSource);
//	        factory.setUserDnPatterns("uid={0},ou=people");
//	        factory.setUserDetailsContextMapper(new PersonContextMapper());
//	        return factory.createAuthenticationManager();
//	    }
	
//	@Bean
//	protected void configureAuthentication() {
//		
//	}
	
	
}
