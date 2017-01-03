package com.se.sat.app.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.se.sat.app.CustomAuthenticationSuccessHandler;
import com.se.sat.app.ProperCookieClearingLogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Resource
	private UserDetailsService customUserDetailsService;
	
	@Autowired
    private SessionRegistry sessionRegistry;
	
	@Bean
	public LogoutSuccessHandler customLogoutSuccessHandler() {
		return new LogoutSuccessHandler() {

			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication authentication) throws IOException, ServletException {

				if (authentication != null) {
					//System.out.println(authentication.getName());
				}
				
				// perform other required operation
				String URL = request.getContextPath() + "/logout";
				response.setStatus(HttpStatus.OK.value());
				response.sendRedirect(URL);

			}
		};
	}
	
	@Value("${rememberMe.privateKey}")
	private String myPrivateKey;

	/** 
	 * The persistent-login authentication able to remember the identity of a principal between sessions
	 */
	@Bean
	public RememberMeServices rememberMeServices() {
		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(myPrivateKey,
				customUserDetailsService);
		rememberMeServices.setAlwaysRemember(false);
		rememberMeServices.setTokenValiditySeconds(86400);
		return rememberMeServices;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.successHandler(new CustomAuthenticationSuccessHandler())
				.permitAll()
			.and()
				.logout().logoutUrl("/logout")
				//.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.addLogoutHandler(new ProperCookieClearingLogoutHandler("JSESSIONID"))
				.permitAll()
			.and()
				.rememberMe()
				.key(myPrivateKey)
				.rememberMeServices(rememberMeServices())
			.and()
				.csrf()
			.and()
				.exceptionHandling().accessDeniedPage("/access-denied")
			.and()
				.sessionManagement()
				.invalidSessionUrl("/session-expired")
				.sessionFixation()
				.changeSessionId()
		        .maximumSessions(1)
		        .maxSessionsPreventsLogin(true)
		        .expiredUrl("/session-expired")
		        .sessionRegistry(sessionRegistry);
	
			// .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			// .antMatchers("/teacher/**").access("hasRole('ROLE_ADMIN')")
		http
	        .authorizeRequests()
			.antMatchers("/", "/public/**", "/resources/**", "/custom/**", "/lib/**").permitAll()
			.antMatchers("/signup", "/signup-next", "/session-expired", "/access-denied").anonymous()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/teacher/**").hasRole("TEACHER")
			.antMatchers("/student/**").hasRole("STUDENT")
			
			.anyRequest().authenticated();
		
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

}
