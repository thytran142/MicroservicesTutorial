package com.broadleafsamples.tutorials.services.catalog.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.broadleafcommerce.catalog.provider.jpa.autoconfigure.CatalogJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryFactoryBean;
import com.broadleafsamples.tutorials.services.catalog.repository.RecipeRepository;

@ConditionalOnProperty(name = "broadleaf.database.provider", havingValue = "jpa")
@Configuration
@EnableJpaRepositories(basePackageClasses = RecipeRepository.class,
        repositoryFactoryBeanClass = JpaTrackableRepositoryFactoryBean.class,
        entityManagerFactoryRef = "catalogEntityManagerFactory",
        transactionManagerRef = "catalogTransactionManager")
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain",
        routePackage = "com.broadleafcommerce.catalog")
@AutoConfigureAfter(CatalogJpaAutoConfiguration.class)
public class TutorialCatalogConfig {
}
