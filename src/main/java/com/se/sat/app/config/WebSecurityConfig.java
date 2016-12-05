package com.se.sat.app.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
//	@Value("${rememberMe.privateKey}")
//	private String myPrivateKey;
	
	@Resource
	private UserDetailsService customUserDetailsService;
	
//	@Bean
//	public RememberMeServices rememberMeServices(){
//		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(myPrivateKey, customUserDetailsService);
//		return rememberMeServices;
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(
				"/",
				"/home",
				"/signup",
				"/forgot-password",
				"/reset-password/**",
				"/error",
				"/public/**",
				"/resources/**",
				"/custom/**",
				"/lib/**"
				).permitAll()
		.anyRequest().authenticated();
		
		http.formLogin().loginPage("/login").permitAll()
			//.and().rememberMe().key(myPrivateKey).rememberMeServices(rememberMeServices())
			.and().logout().permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
}
