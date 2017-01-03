package com.se.sat.app.config;

import java.util.Locale;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.se.sat.app.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class AppConfig extends WebMvcConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }
	
	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler(){
		return new CustomAuthenticationSuccessHandler();
	}
	
	@Bean
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }
	
	@Bean(name = "templateResolver")
	public ServletContextTemplateResolver getTemplateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setOrder(1);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setTemplateMode("HTML5");
		// make change immediately when html is edited
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	
//	@Bean(name="localValidator")
//	public Validator getValidatorFactory(){
//		return new LocalValidatorFactoryBean();
//	}

	@Bean(name = "templateEngine")
	public SpringTemplateEngine getTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addDialect(new SpringSecurityDialect());
		templateEngine.setTemplateResolver(getTemplateResolver());
		return templateEngine;
	}

	@Bean(name = "viewResolver")
	public ThymeleafViewResolver getViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(getTemplateEngine());
		return viewResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("WEB-INF/i18n/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(10);
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("en"));
		localeResolver.setCookieName("myLocaleCookie");
		localeResolver.setCookieMaxAge(4800);
		return localeResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/custom/**").addResourceLocations("classpath:static/").setCachePeriod(86400);
		registry.addResourceHandler("/lib/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(86400);
	}

	// Hibernate sector
	@Autowired
	private Environment environment;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.se.sat.app.entity" });
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
		dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
		dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));

		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));

		return properties;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
