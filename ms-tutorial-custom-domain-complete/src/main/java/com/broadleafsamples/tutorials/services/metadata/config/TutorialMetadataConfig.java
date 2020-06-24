package com.broadleafsamples.tutorials.services.metadata.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.broadleafcommerce.customer.metadata.customer.CustomerAddressFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerFields;
import com.broadleafcommerce.customer.metadata.customer.CustomerForms;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;

public class TutorialMetadataConfig {
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
