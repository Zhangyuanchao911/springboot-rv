package com.xdd.springrvmp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author AixLeft
 * Date 2021/1/19
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/unauthorized").setViewName("unauthorized");
        registry.addViewController("/toLogin").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/bootm").setViewName("bootm");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/view/brand/brand_add").setViewName("view/brand/brand_add");

    }
}
