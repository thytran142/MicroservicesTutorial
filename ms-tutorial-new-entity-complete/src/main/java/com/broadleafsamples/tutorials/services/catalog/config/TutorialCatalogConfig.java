package com.broadleafsamples.tutorials.services.catalog.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.broadleafcommerce.catalog.provider.jpa.autoconfigure.CatalogJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.data.tracking.jpa.filtering.EnableJpaTrackableFlows;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryFactoryBean;
import com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain.JpaRecipe;
import com.broadleafsamples.tutorials.services.catalog.repository.ProductRecipeRepository;

import static com.broadleafcommerce.catalog.provider.jpa.Constants.Persistence.CATALOG_ROUTE_KEY;
import static com.broadleafcommerce.catalog.provider.jpa.Constants.Persistence.CATALOG_ROUTE_PACKAGE;

@ConditionalOnProperty(name = "broadleaf.database.provider", havingValue = "jpa")
@Configuration
@EnableJpaRepositories(basePackageClasses = ProductRecipeRepository.class,
        repositoryFactoryBeanClass = JpaTrackableRepositoryFactoryBean.class,
        entityManagerFactoryRef = "catalogEntityManagerFactory",
        transactionManagerRef = "catalogTransactionManager")
@EnableJpaTrackableFlows(entityClasses = JpaRecipe.class, routeKey = CATALOG_ROUTE_KEY)
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain",
        routePackage = CATALOG_ROUTE_PACKAGE)
@AutoConfigureAfter(CatalogJpaAutoConfiguration.class)
public class TutorialCatalogConfig {
}
