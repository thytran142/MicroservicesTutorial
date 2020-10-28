package com.broadleafsamples.tutorials.services.metadata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.broadleafcommerce.catalog.metadata.support.ProductIds;
import com.broadleafcommerce.metadata.dsl.core.extension.views.details.CreateEntityView;
import com.broadleafcommerce.metadata.dsl.core.extension.views.details.EntityFormView;
import com.broadleafcommerce.metadata.dsl.core.extension.views.details.UpdateEntityView;
import com.broadleafcommerce.metadata.dsl.core.utils.Fields;
import com.broadleafcommerce.metadata.dsl.registry.ComponentSource;
import com.broadleafsamples.tutorials.services.metadata.support.TutorialProductProps;

@Configuration
public class TutorialMetadataConfig {

    @Configuration
    public static class Catalog {

        @Bean
        public ComponentSource tutorialProductMetadataOverrides() {
            return registry -> {
                CreateEntityView<?> productCreate =
                        (CreateEntityView<?>) registry.get(ProductIds.CREATE);
                productCreate.generalForm(this::addTutorialProductFields);

                UpdateEntityView<?> productUpdate =
                        (UpdateEntityView<?>) registry.get(ProductIds.UPDATE);
                productUpdate.generalForm(this::addTutorialProductFields);
            };
        }

        protected EntityFormView<?> addTutorialProductFields(EntityFormView<?> form) {
            return form
                    .addField(TutorialProductProps.MY_PROPERTY, Fields.string()
                            .label("My Property")
                            .order(20000));
        }


    }
    
}
