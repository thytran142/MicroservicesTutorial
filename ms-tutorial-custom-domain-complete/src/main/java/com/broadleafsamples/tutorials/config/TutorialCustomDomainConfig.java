package com.broadleafsamples.tutorials.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import com.broadleafcommerce.common.jpa.data.entity.JpaEntityScan;
import com.broadleafcommerce.customer.provider.jpa.autoconfigure.CustomerJpaAutoConfiguration;

@Configuration
@JpaEntityScan(basePackages = "com.broadleafsamples.tutorials.domain",
        routePackage = "com.broadleafcommerce.customer")
@AutoConfigureAfter(CustomerJpaAutoConfiguration.class)
public class TutorialCustomDomainConfig {

}