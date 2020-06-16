package com.broadleafsamples.tutorials;

import org.junit.jupiter.api.Nested;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static com.broadleafcommerce.common.jpa.schema.SchemaCompatibiltyUtility.PostgresUtilityProvider;

/**
 * Verify that we can start up against RDBMS using the known schema configuration. The
 * {@code Utility} test class is intended for use by developers to keep JPA entity changes in sync
 * with the liquibase change logs.
 *
 * @author Jeff Fischer
 */
public class UtilitiesIT {

    // @formatter:off
    /**
     *  #####                               #     #
     * #     # #####   ##   #####  #####    #     # ###### #####  ######
     * #         #    #  #  #    #   #      #     # #      #    # #
     *  #####    #   #    # #    #   #      ####### #####  #    # #####
     *       #   #   ###### #####    #      #     # #      #####  #
     * #     #   #   #    # #   #    #      #     # #      #   #  #
     *  #####    #   #    # #    #   #      #     # ###### #    # ######
     *
     * Execute these utility tests directly from the IDE in order to update the liquibase
     * change logs for each supported RDBMS platform based on the current JPA entity state. Updated
     * Liquibase change logs are emitted at src/main/resources/db/changelog.
     */
    // @formatter:on
    public static class AllUtilities {

        /***
         * Specifically defining the test profile that excludes
         * all the services other than catalog in order to initiate
         * the changelog diff generation tool.
         */
        @TestPropertySource(properties = {"spring.liquibase.enabled=false", "service.key=customer",
                "client.prefix=tutorialdata"})
        @Nested
        @ActiveProfiles("test")
        public class PostgresUtility extends PostgresUtilityProvider {}

    }
    
}
