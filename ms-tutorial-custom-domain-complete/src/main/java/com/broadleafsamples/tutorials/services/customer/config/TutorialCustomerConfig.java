package com.broadleafsamples.tutorials.services.customer.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.customer.provider.jpa.autoconfigure.CustomerJpaAutoConfiguration;
import com.broadleafcommerce.data.tracking.jpa.filtering.auto.EnableJpaTrackableFlow;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryFactoryBean;
import com.broadleafsamples.tutorials.services.customer.provider.jpa.domain.JpaMovie;
import com.broadleafsamples.tutorials.services.customer.repository.CustomerMovieRepository;

import static com.broadleafcommerce.customer.provider.RouteConstants.Persistence.CUSTOMER_ROUTE_KEY;
import static com.broadleafcommerce.customer.provider.RouteConstants.Persistence.CUSTOMER_ROUTE_PACKAGE;

@ConditionalOnProperty(name = "broadleaf.database.provider", havingValue = "jpa")
@Configuration
@EnableJpaRepositories(basePackageClasses = CustomerMovieRepository.class,
                    repositoryFactoryBeanClass = JpaTrackableRepositoryFactoryBean.class,
                    entityManagerFactoryRef = "customerEntityManagerFactory",
                    transactionManagerRef = "catalogTransactionManager")
@EnableJpaTrackableFlow(entityClass = JpaMovie.class, routeKey = CUSTOMER_ROUTE_KEY,
                    permissionRoots = "CUSTOMER", rootPath = "/movies", projectionName = "Movie")
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.services.customer.provider.jpa.domain",
                    routePackage = CUSTOMER_ROUTE_PACKAGE)
@AutoConfigureAfter(CustomerJpaAutoConfiguration.class)
public class TutorialCustomerConfig { }
