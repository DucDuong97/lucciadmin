package com.lucci.webadmin.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    private static final String IMAGE_DIR_NAME = "images";
    private static final String IMAGE_DIR_ABS_PATH = "file:/"+ Paths.get(IMAGE_DIR_NAME).toFile().getAbsolutePath() + "/";
    protected static final String[] RESOURCE_LOCATIONS = new String[]{
        "classpath:/static/app/", "classpath:/static/content/", "classpath:/static/i18n/", IMAGE_DIR_ABS_PATH};
    protected static final String[] RESOURCE_PATHS = new String[]{
        "/app/*", "/content/*", "/i18n/*", "/" + IMAGE_DIR_NAME + "/**"};

    private final JHipsterProperties jhipsterProperties;

    public StaticResourcesWebConfiguration(JHipsterProperties jHipsterProperties) {
        this.jhipsterProperties = jHipsterProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(getJHipsterHttpCacheProperty(), TimeUnit.DAYS).cachePublic();
    }

    private int getJHipsterHttpCacheProperty() {
        return jhipsterProperties.getHttp().getCache().getTimeToLiveInDays();
    }

}
