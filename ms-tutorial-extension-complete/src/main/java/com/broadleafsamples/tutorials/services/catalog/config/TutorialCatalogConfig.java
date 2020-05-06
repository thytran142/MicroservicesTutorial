package com.broadleafsamples.tutorials.services.catalog.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.broadleafcommerce.catalog.domain.product.Product;
import com.broadleafcommerce.catalog.domain.product.Variant;
import com.broadleafcommerce.catalog.provider.jpa.autoconfigure.CatalogJpaAutoConfiguration;
import com.broadleafcommerce.catalog.repository.product.ProductRepository;
import com.broadleafcommerce.catalog.service.product.ProductService;
import com.broadleafcommerce.catalog.service.product.VariantService;
import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.data.tracking.core.Trackable;
import com.broadleafcommerce.data.tracking.core.service.RsqlCrudEntityHelper;
import com.broadleafsamples.tutorials.services.catalog.service.TutorialProductService;

@Configuration
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.services.catalog.provider.jpa.domain",
        routePackage = "com.broadleafcommerce.catalog")
@AutoConfigureAfter(CatalogJpaAutoConfiguration.class)
public class TutorialCatalogConfig {

    @Bean
    public ProductService<Product> customProductService(
        ProductRepository<Trackable> productRepository,
        RsqlCrudEntityHelper helper,
        VariantService<Variant> variantService) {
        return new TutorialProductService(productRepository, helper, variantService);
    }

}
