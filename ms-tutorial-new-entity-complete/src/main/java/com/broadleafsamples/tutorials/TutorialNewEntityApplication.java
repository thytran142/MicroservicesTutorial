package com.broadleafsamples.tutorials;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.broadleafcommerce.common.extension.TypeFactory;
import com.broadleafcommerce.data.tracking.core.Trackable;
import com.broadleafcommerce.data.tracking.core.TrackableBehaviorUtil;
import com.broadleafcommerce.data.tracking.core.context.ContextInfo;
import com.broadleafcommerce.data.tracking.core.mapping.ContextStateBuilder;
import com.broadleafcommerce.resource.security.SecurityEnhancer;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.apachecommons.CommonsLog;

@SpringBootApplication
public class TutorialNewEntityApplication {

    @Bean
    SecurityEnhancer tenantAnonymous() {
        return http -> {
            http.authorizeRequests().antMatchers("/resolver/**").permitAll();
            http.authorizeRequests().antMatchers("/url-resolver/**").permitAll();
        };
    }

    @Component
    @ConditionalOnProperty(name = "broadleaf.common.policy.validation.enforce",
            havingValue = "false")
    @CommonsLog
    static class AlwaysMutableContextStateBuilder extends ContextStateBuilder {

        public AlwaysMutableContextStateBuilder(TypeFactory factory,
                                                TrackableBehaviorUtil behaviorUtil,
                                                ObjectMapper fieldChangeMapper,
                                                ModelMapper cloneMapper) {
            super(factory, behaviorUtil, fieldChangeMapper, cloneMapper);
            log.info("Loaded AlwaysMutableContextStateBuilder");
        }

        @Override
        protected boolean determineContextStateMutability(Trackable domain,
                                                          ContextInfo contextInfo) {
            return true;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(TutorialNewEntityApplication.class, args);
    }

}
