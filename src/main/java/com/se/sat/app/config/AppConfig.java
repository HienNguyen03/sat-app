package com.se.sat.app.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {
	
	//	@Bean
	//	public InternalResourceViewResolver viewResolver(){
	//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	//		viewResolver.setPrefix("/WEB-INF/jsp/");
	//		viewResolver.setSuffix(".jsp");
	//		viewResolver.setPrefix("/WEB-INF/pages/");
	//		viewResolver.setSuffix(".jsp");
	//		return viewResolver;
	//	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/custom/**").addResourceLocations("classpath:static/");
        registry.addResourceHandler("/lib/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	    
	}

	@Bean
	public MessageSource messageSource(){
		
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/WEB-INF/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(1800);
		return messageSource;
		
	}
	
	@Bean
	public LocaleResolver localeResolver(){
		
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		localeResolver.setCookieName("myLocaleCookie");
		localeResolver.setCookieMaxAge(4800);
		return localeResolver;
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("language");
		registry.addInterceptor(interceptor);
		
	}
	
	
	
}
