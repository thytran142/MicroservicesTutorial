package com.broadleafsamples.tutorials.services.customer.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.customer.metadata.customer.CustomerAddressFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerForms;
import com.broadleafcommerce.customer.provider.jpa.autoconfigure.CustomerJpaAutoConfiguration;
import com.broadleafcommerce.data.tracking.jpa.filtering.auto.EnableJpaTrackableFlow;
import com.broadleafcommerce.data.tracking.jpa.filtering.narrow.factory.JpaTrackableRepositoryFactoryBean;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;
import com.broadleafsamples.tutorials.services.customer.provider.jpa.domain.JpaMovie;
import com.broadleafsamples.tutorials.services.customer.repository.MovieRepository;

import static com.broadleafcommerce.customer.provider.RouteConstants.Persistence.CUSTOMER_ROUTE_KEY;
import static com.broadleafcommerce.customer.provider.RouteConstants.Persistence.CUSTOMER_ROUTE_PACKAGE;

@ConditionalOnProperty(name = "broadleaf.database.provider", havingValue = "jpa")
@Configuration
@EnableJpaRepositories(basePackageClasses = MovieRepository.class,
                    repositoryFactoryBeanClass = JpaTrackableRepositoryFactoryBean.class,
                    entityManagerFactoryRef = "customerEntityManagerFactory",
                    transactionManagerRef = "catalogTransactionManager")
@EnableJpaTrackableFlow(entityClass = JpaMovie.class, routeKey = CUSTOMER_ROUTE_KEY,
                    permissionRoots = "CUSTOMER", rootPath = "/movies", projectionName = "Movie")
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.services.customer.provider.jpa.domain",
                    routePackage = CUSTOMER_ROUTE_PACKAGE)
@AutoConfigureAfter(CustomerJpaAutoConfiguration.class)
public class TutorialCustomerConfig {

    @Component
    class TutorialCustomerFields extends CustomerFields {

        public static final String FAVORITE_MOVIES_PROPERTY = "favoriteMovies";

        public TutorialCustomerFields() {
            super();
            add(FieldComponent.builder(FAVORITE_MOVIES_PROPERTY)
                    .label("Favorite Movies")
                    .required(false)
                    .translatable(false));
        }
    }

    @Component
    class TutorialCustomerForms extends CustomerForms {

        public TutorialCustomerForms(CustomerFields customerFields,
                                     CustomerAddressFields customerAddressFields,
                                     Environment environment) {
            super(customerFields,
                    customerAddressFields,
                    environment.getProperty("broadleaf.customer.useFullName", Boolean.class,
                            Boolean.TRUE));
        }

        @Override
        protected EntityFormBuilder generalForm(String id) {
            return super.generalForm(id)
                    .addField(getCustomerFields().get(TutorialCustomerFields.FAVORITE_MOVIES_PROPERTY)
                            .order(20000).build());
        }
    }
}
