/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.htmlscraping.app.configuration;

import com.htmlscraping.app.HtmlAnalysisMarker;
import com.htmlscraping.app.service.CachingService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@PropertySource(name = "htmlProperties",
        value = {"classpath:htmlVersions.properties"},
        ignoreResourceNotFound = false)
@PropertySource(name = "systemProperties",
        value = {"classpath:systemProperties.properties"},
        ignoreResourceNotFound = false)
@ComponentScan(basePackageClasses = {HtmlAnalysisMarker.class})
public class SystemConfiguration extends WebMvcConfigurerAdapter{
    
    
    @Autowired
    CachingService cachingService;
    
    @PostConstruct
    public void intialize() {
        
        cachingService.setHtmlVersions();
        cachingService.setSystemProperties();

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
        rb.setBasenames(new String[]{"messages/messages", "messages/validation"});
        return rb;
    }

}
