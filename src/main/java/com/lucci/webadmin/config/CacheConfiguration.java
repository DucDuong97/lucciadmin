package com.lucci.webadmin.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.lucci.webadmin.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.lucci.webadmin.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.lucci.webadmin.domain.User.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Authority.class.getName());
            createCache(cm, com.lucci.webadmin.domain.User.class.getName() + ".authorities");
            createCache(cm, com.lucci.webadmin.domain.ServiceOption.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Policy.class.getName());
            createCache(cm, com.lucci.webadmin.domain.CustomerReview.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Achievement.class.getName());
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName());
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".options");
            createCache(cm, com.lucci.webadmin.domain.Blog.class.getName());
            createCache(cm, com.lucci.webadmin.domain.SingletonContent.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Page.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Person.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Patient.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Patient.class.getName() + ".bookings");
            createCache(cm, com.lucci.webadmin.domain.Patient.class.getName() + ".treatments");
            createCache(cm, com.lucci.webadmin.domain.Doctor.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Doctor.class.getName() + ".treatments");
            createCache(cm, com.lucci.webadmin.domain.Nurse.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Nurse.class.getName() + ".treatments");
            createCache(cm, com.lucci.webadmin.domain.Receptionist.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Receptionist.class.getName() + ".payments");
            createCache(cm, com.lucci.webadmin.domain.Receptionist.class.getName() + ".getMaterials");
            createCache(cm, com.lucci.webadmin.domain.Accountant.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Material.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Booking.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Payment.class.getName());
            createCache(cm, com.lucci.webadmin.domain.MedicalRecord.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Treatment.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Treatment.class.getName() + ".histories");
            createCache(cm, com.lucci.webadmin.domain.Treatment.class.getName() + ".doctors");
            createCache(cm, com.lucci.webadmin.domain.Treatment.class.getName() + ".nurses");
            createCache(cm, com.lucci.webadmin.domain.TreatmentHistory.class.getName());
            createCache(cm, com.lucci.webadmin.domain.GetMaterial.class.getName());
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".processes");
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".blogs");
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".relatedVideos");
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".customerImgUrls");
            createCache(cm, com.lucci.webadmin.domain.Process.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Video.class.getName());
            createCache(cm, com.lucci.webadmin.domain.ImgUrl.class.getName());
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".relatedBlogs");
            createCache(cm, com.lucci.webadmin.domain.ServiceItem.class.getName() + ".pricingCards");
            createCache(cm, com.lucci.webadmin.domain.PricingCard.class.getName());
            createCache(cm, com.lucci.webadmin.domain.PricingCard.class.getName() + ".contents");
            createCache(cm, com.lucci.webadmin.domain.PricingContent.class.getName());
            createCache(cm, com.lucci.webadmin.domain.Branch.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
