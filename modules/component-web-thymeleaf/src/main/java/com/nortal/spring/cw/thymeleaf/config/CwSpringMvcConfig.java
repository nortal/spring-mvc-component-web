package com.nortal.spring.cw.thymeleaf.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.servlet.mvc.interceptor.CwLocaleInterceptor;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 19.05.2015
 */
@Configuration
@ComponentScan(basePackages = { "com.nortal.spring.cw.thymeleaf.web.portal",
		"com.nortal.spring.cw.jsp.web.portal.menu" })
public class CwSpringMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private Environment env;
	@Autowired
	private CwLocaleInterceptor localeInterceptor;

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {

	}

	@Bean(name = "messageSource")
	public CwMessageSource messageSource() {
		CwMessageSource cachedMessageSource = new CwMessageSource();
		cachedMessageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
		cachedMessageSource.setAlwaysUseMessageFormat(Boolean.TRUE);
		return cachedMessageSource;
	}

	@Bean(name = "messageSourceAccessor")
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(messageSource());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
		webContentInterceptor.setCacheSeconds(0);
		webContentInterceptor.setUseExpiresHeader(true);
		webContentInterceptor.setUseCacheControlHeader(true);
		webContentInterceptor.setUseCacheControlNoStore(true);

		registry.addInterceptor(webContentInterceptor);

		registry.addInterceptor(localeInterceptor);

	}

	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		// vastasel juhul truncate-itakse urlid "." juures by default ära
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/js/")
				.setCachePeriod(3000);
		registry.addResourceHandler("/gfx/**").addResourceLocations("/gfx/")
				.setCachePeriod(3000);
		registry.addResourceHandler("/static/**")
				.addResourceLocations("/static/").setCachePeriod(3000);
		registry.addResourceHandler("/favicon.ico")
				.addResourceLocations("/favicon.ico").setCachePeriod(3000);
		super.addResourceHandlers(registry);
	}

	@Bean
	public ViewResolver viewResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setPrefix("views/");
		templateResolver.setSuffix(".html");

		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver);

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(engine);
		return viewResolver;
	}
}
