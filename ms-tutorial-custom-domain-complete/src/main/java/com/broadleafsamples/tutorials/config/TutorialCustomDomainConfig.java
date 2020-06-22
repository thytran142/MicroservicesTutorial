package com.broadleafsamples.tutorials.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.customer.metadata.customer.CustomerAddressFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerForms;
import com.broadleafcommerce.customer.provider.jpa.autoconfigure.CustomerJpaAutoConfiguration;
import com.broadleafcommerce.metadata.domain.CollectionMapping;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.Mapping;
import com.broadleafcommerce.metadata.domain.PropertyMapping;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;
import com.broadleafcommerce.metadata.domain.type.FieldType;

import java.util.Arrays;

@Configuration
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.domain",
        routePackage = "com.broadleafcommerce.customer")
@AutoConfigureAfter(CustomerJpaAutoConfiguration.class)
public class TutorialCustomDomainConfig {

    @Component
    class TutorialCustomerFields extends CustomerFields {

        public static final String FAVORITE_MOVIES_PROPERTY = "favoriteMovies";

        public TutorialCustomerFields() {
            super();
            add(FieldComponent.builder(FieldType.Collection.RESIDENT_GRID, FAVORITE_MOVIES_PROPERTY)
                    .label("Favorite Movies")
                    .required(false)
                    .translatable(false)
                    .components();
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