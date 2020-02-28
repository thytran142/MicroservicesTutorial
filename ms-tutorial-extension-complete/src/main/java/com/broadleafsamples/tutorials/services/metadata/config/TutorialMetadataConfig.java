package com.broadleafsamples.tutorials.services.metadata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.broadleafcommerce.catalog.metadata.product.CommonPriceDataComponents;
import com.broadleafcommerce.catalog.metadata.product.IncludedProductFields;
import com.broadleafcommerce.catalog.metadata.product.NonSkuPriceDataComponents;
import com.broadleafcommerce.catalog.metadata.product.ProductFields;
import com.broadleafcommerce.catalog.metadata.product.ProductForms;
import com.broadleafcommerce.catalog.metadata.product.ProductOptionFields;
import com.broadleafcommerce.catalog.metadata.product.ProductOptionForms;
import com.broadleafcommerce.catalog.metadata.product.PromotionalProductFields;
import com.broadleafcommerce.catalog.metadata.product.VariantFields;
import com.broadleafcommerce.catalog.metadata.product.pricing.PriceDataFields;
import com.broadleafcommerce.metadata.domain.FieldComponent;
import com.broadleafcommerce.metadata.domain.builder.EntityFormBuilder;

@Configuration
public class TutorialMetadataConfig {

    @Component
    class TutorialProductFields extends ProductFields {

        public static final String MY_PROPERTY = "myProperty";

        public TutorialProductFields() {
            super();
            add(FieldComponent.builder(MY_PROPERTY)
                    .label("My Property")
                    .required(false)
                    .translatable(false));
        }

    }

    @Component
    class TutorialProductForms extends ProductForms {

        public TutorialProductForms(ProductFields productFields,
                ProductOptionFields productOptionFields, VariantFields variantFields,
                PromotionalProductFields promotionalProductFields,
                IncludedProductFields includedProductFields, PriceDataFields priceDataFields,
                CommonPriceDataComponents commonPriceDataComponents, ProductOptionForms optionForms,
                NonSkuPriceDataComponents nonSkuPriceDataComponents) {
            super(productFields, productOptionFields, variantFields, promotionalProductFields,
                    includedProductFields, priceDataFields, commonPriceDataComponents, optionForms,
                    nonSkuPriceDataComponents);
        }

        @Override
        protected EntityFormBuilder generalForm() {
            return super.generalForm()
                    .addField(getProductFields().get(TutorialProductFields.MY_PROPERTY)
                            .order(20000).build());
        }
    }
}
