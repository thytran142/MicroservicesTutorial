package com.broadleafsamples.tutorials.services.catalog.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.broadleafcommerce.catalog.dataexport.converter.DimensionsExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.converter.ToStringConverter;
import com.broadleafcommerce.catalog.dataexport.converter.WeightExportRowConverter;
import com.broadleafcommerce.catalog.dataexport.specification.ProductExportSpecification;
import com.broadleafcommerce.catalog.provider.jpa.autoconfigure.CatalogJpaAutoConfiguration;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafsamples.tutorials.services.catalog.dataexport.converter.TutorialProductExportRowConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain",
        routePackage = "com.broadleafcommerce.catalog")
@AutoConfigureAfter(CatalogJpaAutoConfiguration.class)
public class TutorialCatalogConfig {

    @Bean
    public TutorialProductExportRowConverter customProductExportRowConverter(
            ProductExportSpecification specification,
            ObjectMapper objectMapper,
            ToStringConverter<Object> toStringConverter,
            DimensionsExportRowConverter dimensionsExportRowConverter,
            WeightExportRowConverter weightExportRowConverter) {
        return new TutorialProductExportRowConverter(specification,
                objectMapper,
                toStringConverter,
                dimensionsExportRowConverter,
                weightExportRowConverter);
    }

}
